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

import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Dave Syer
 */
@Controller
class VisitController {

	private final OccupantRepository owners;

	public VisitController(OccupantRepository owners) {
		this.owners = owners;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	/**
	 * Called before each and every @RequestMapping annotated method. 2 goals: - Make sure
	 * we always have fresh data - Since we do not use the session scope, make sure that
	 * Room object always has an id (Even though id is not part of the form fields)
	 * @param roomId
	 * @return Room
	 */
	@ModelAttribute("visit")
	public Visit loadRoomWithVisit(@PathVariable("ownerId") int ownerId, @PathVariable("roomId") int roomId,
			Map<String, Object> model) {
		Occupant owner = this.owners.findById(ownerId);
		Room room = owner.getRoom(roomId);
		model.put("room", room);
		model.put("owner", owner);
		Visit visit = new Visit();
		room.addVisit(visit);
		return visit;
	}

	// Spring MVC calls method loadRoomWithVisit(...) before initNewVisitForm is
	// called
	@GetMapping("/owners/{ownerId}/rooms/{roomId}/visits/new")
	public String initNewVisitForm(@PathVariable("roomId") int roomId, Map<String, Object> model) {
		return "rooms/createOrUpdateVisitForm";
	}

	// Spring MVC calls method loadRoomWithVisit(...) before processNewVisitForm is
	// called
	@PostMapping("/owners/{ownerId}/rooms/{roomId}/visits/new")
	public String processNewVisitForm(@ModelAttribute Occupant owner, @PathVariable int roomId, @Valid Visit visit,
			BindingResult result) {
		if (result.hasErrors()) {
			return "rooms/createOrUpdateVisitForm";
		}
		else {
			owner.addVisit(roomId, visit);
			this.owners.save(owner);
			return "redirect:/owners/{ownerId}";
		}
	}

}
