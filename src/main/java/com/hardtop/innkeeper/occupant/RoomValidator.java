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

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * <code>Validator</code> for <code>Room</code> forms.
 * <p>
 * We're not using Bean Validation annotations here because it is easier to define such
 * validation rule in Java.
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 */
public class RoomValidator implements Validator {

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object obj, Errors errors) {
		Room room = (Room) obj;
		String name = room.getName();
		// name validation
		if (!StringUtils.hasLength(name)) {
			errors.rejectValue("name", REQUIRED, REQUIRED);
		}

		// type validation
		if (room.isNew() && room.getType() == null) {
			errors.rejectValue("type", REQUIRED, REQUIRED);
		}

		// birth date validation
		if (room.getBirthDate() == null) {
			errors.rejectValue("birthDate", REQUIRED, REQUIRED);
		}
	}

	/**
	 * This Validator validates *just* Room instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Room.class.isAssignableFrom(clazz);
	}

}
