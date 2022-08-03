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

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
class OccupantController {

	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "occupants/createOrUpdateOccupantForm";

	private final OccupantRepository occupants;

	public OccupantController(OccupantRepository clinicService) {
		this.occupants = clinicService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping("/occupants/new")
	public String initCreationForm(Map<String, Object> model) {
		Occupant occupant = new Occupant();
		model.put("occupant", occupant);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/occupants/new")
	public String processCreationForm(@Valid Occupant occupant, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		else {
			this.occupants.save(occupant);
			return "redirect:/occupants/" + occupant.getId();
		}
	}

	@GetMapping("/occupants/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("occupant", new Occupant());
		return "occupants/findOccupants";
	}

	@GetMapping("/occupants")
	public String processFindForm(@RequestParam(defaultValue = "1") int page, Occupant occupant, BindingResult result,
			Model model) {

		// allow parameterless GET request for /occupants to return all records
		if (occupant.getLastName() == null) {
			occupant.setLastName(""); // empty string signifies broadest possible search
		}

		// find occupants by last name
		String lastName = occupant.getLastName();
		Page<Occupant> occupantsResults = findPaginatedForOccupantsLastName(page, lastName);
		if (occupantsResults.isEmpty()) {
			// no occupants found
			result.rejectValue("lastName", "notFound", "not found");
			return "occupants/findOccupants";
		}
		else if (occupantsResults.getTotalElements() == 1) {
			// 1 occupant found
			occupant = occupantsResults.iterator().next();
			return "redirect:/occupants/" + occupant.getId();
		}
		else {
			// multiple occupants found
			lastName = occupant.getLastName();
			return addPaginationModel(page, model, lastName, occupantsResults);
		}
	}

	private String addPaginationModel(int page, Model model, String lastName, Page<Occupant> paginated) {
		model.addAttribute("listOccupants", paginated);
		List<Occupant> listOccupants = paginated.getContent();
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", paginated.getTotalPages());
		model.addAttribute("totalItems", paginated.getTotalElements());
		model.addAttribute("listOccupants", listOccupants);
		return "occupants/occupantsList";
	}

	private Page<Occupant> findPaginatedForOccupantsLastName(int page, String lastname) {

		int pageSize = 5;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		return occupants.findByLastName(lastname, pageable);

	}

	@GetMapping("/occupants/{occupantId}/edit")
	public String initUpdateOccupantForm(@PathVariable("occupantId") int occupantId, Model model) {
		Occupant occupant = this.occupants.findById(occupantId);
		model.addAttribute(occupant);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/occupants/{occupantId}/edit")
	public String processUpdateOccupantForm(@Valid Occupant occupant, BindingResult result,
			@PathVariable("occupantId") int occupantId) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		else {
			occupant.setId(occupantId);
			this.occupants.save(occupant);
			return "redirect:/occupants/{occupantId}";
		}
	}

	/**
	 * Custom handler for displaying an occupant.
	 * @param occupantId the ID of the occupant to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/occupants/{occupantId}")
	public ModelAndView showOccupant(@PathVariable("occupantId") int occupantId) {
		ModelAndView mav = new ModelAndView("occupants/occupantDetails");
		Occupant occupant = this.occupants.findById(occupantId);
		mav.addObject(occupant);
		return mav;
	}

}
