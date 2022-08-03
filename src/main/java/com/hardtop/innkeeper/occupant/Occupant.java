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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import org.springframework.core.style.ToStringCreator;
import com.hardtop.innkeeper.model.Person;
import org.springframework.util.Assert;

/**
 * Simple JavaBean domain object representing an occupant.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Oliver Drotbohm
 */
@Entity
@Table(name = "occupants")
public class Occupant extends Person {

	@Column(name = "address")
	@NotEmpty
	private String address;

	@Column(name = "city")
	@NotEmpty
	private String city;

	@Column(name = "telephone")
	@NotEmpty
	@Digits(fraction = 0, integer = 10)
	private String telephone;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "occupant_id")
	@OrderBy("name")
	private List<Room> rooms = new ArrayList<>();

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public List<Room> getRooms() {
		return this.rooms;
	}

	public void addRoom(Room room) {
		if (room.isNew()) {
			getRooms().add(room);
		}
	}

	/**
	 * Return the Room with the given name, or null if none found for this Occupant.
	 * @param name to test
	 * @return true if room name is already in use
	 */
	public Room getRoom(String name) {
		return getRoom(name, false);
	}

	/**
	 * Return the Room with the given id, or null if none found for this Occupant.
	 * @param name to test
	 * @return a room if room id is already in use
	 */
	public Room getRoom(Integer id) {
		for (Room room : getRooms()) {
			if (!room.isNew()) {
				Integer compId = room.getId();
				if (compId.equals(id)) {
					return room;
				}
			}
		}
		return null;
	}

	/**
	 * Return the Room with the given name, or null if none found for this Occupant.
	 * @param name to test
	 * @return true if room name is already in use
	 */
	public Room getRoom(String name, boolean ignoreNew) {
		name = name.toLowerCase();
		for (Room room : getRooms()) {
			if (!ignoreNew || !room.isNew()) {
				String compName = room.getName();
				compName = compName == null ? "" : compName.toLowerCase();
				if (compName.equals(name)) {
					return room;
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this).append("id", this.getId()).append("new", this.isNew())
				.append("lastName", this.getLastName()).append("firstName", this.getFirstName())
				.append("address", this.address).append("city", this.city).append("telephone", this.telephone)
				.toString();
	}

	/**
	 * Adds the given {@link Visit} to the {@link Room} with the given identifier.
	 * @param roomId the identifier of the {@link Room}, must not be {@literal null}.
	 * @param visit the visit to add, must not be {@literal null}.
	 */
	public Occupant addVisit(Integer roomId, Visit visit) {

		Assert.notNull(roomId, "Room identifier must not be null!");
		Assert.notNull(visit, "Visit must not be null!");

		Room room = getRoom(roomId);

		Assert.notNull(room, "Invalid Room identifier!");

		room.addVisit(visit);

		return this;
	}

}
