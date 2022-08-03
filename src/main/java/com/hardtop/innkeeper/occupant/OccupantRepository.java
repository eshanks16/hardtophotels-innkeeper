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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface OccupantRepository extends Repository<Occupant, Integer> {

	@Query("SELECT ptype FROM RoomType ptype ORDER BY ptype.name")
	@Transactional(readOnly = true)
	List<RoomType> findRoomTypes();

	@Query("SELECT DISTINCT occupant FROM Occupant occupant left join  occupant.rooms WHERE occupant.lastName LIKE :lastName% ")
	@Transactional(readOnly = true)
	Page<Occupant> findByLastName(@Param("lastName") String lastName, Pageable pageable);

	@Query("SELECT occupant FROM Occupant occupant left join fetch occupant.rooms WHERE occupant.id =:id")
	@Transactional(readOnly = true)
	Occupant findById(@Param("id") Integer id);

	void save(Occupant occupant);

	@Query("SELECT occupant FROM Occupant occupant")
	@Transactional(readOnly = true)
	Page<Occupant> findAll(Pageable pageable);

}
