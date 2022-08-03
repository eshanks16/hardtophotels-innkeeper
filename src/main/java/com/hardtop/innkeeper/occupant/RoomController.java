/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hardtop.innkeeper.occupant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/occupants/{occupantId}")
class RoomController {

	private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "rooms/createOrUpdateRoomForm";

	private final OccupantRepository occupants;

	public RoomController(OccupantRepository occupants) {
		this.occupants = occupants;
	}

	@ModelAttribute("types")
	public Collection<RoomType> populateRoomTypes() {
		return this.occupants.findRoomTypes();
	}

	@ModelAttribute("occupant")
	public Occupant findOccupant(@PathVariable("occupantId") int occupantId) {
		return this.occupants.findById(occupantId);
	}

	@InitBinder("occupant")
	public void initOccupantBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("room")
	public void initRoomBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new RoomValidator());
	}

	@GetMapping("/rooms/new")
	public String initCreationForm(Occupant occupant, ModelMap model) {
		Room room = new Room();
		occupant.addRoom(room);
		model.put("room", room);
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/rooms/new")
	public String processCreationForm(Occupant occupant, @Valid Room room, BindingResult result, ModelMap model) {
		if (StringUtils.hasLength(room.getName()) && room.isNew() && occupant.getRoom(room.getName(), true) != null) {
			result.rejectValue("name", "duplicate", "already exists");
		}
		occupant.addRoom(room);
		if (result.hasErrors()) {
			model.put("room", room);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		}
		else {
			this.occupants.save(occupant);
			return "redirect:/occupants/{occupantId}";
		}
	}

	@GetMapping("/rooms/{roomId}/edit")
	public String initUpdateForm(Occupant occupant, @PathVariable("roomId") int roomId, ModelMap model) {
		Room room = occupant.getRoom(roomId);
		model.put("room", room);
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/rooms/{roomId}/edit")
	public String processUpdateForm(@Valid Room room, BindingResult result, Occupant occupant, ModelMap model) {
		if (result.hasErrors()) {
			model.put("room", room);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		}
		else {
			occupant.addRoom(room);
			this.occupants.save(occupant);
			return "redirect:/occupants/{occupantId}";
		}
	}

}
