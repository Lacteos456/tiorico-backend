package com.tiorico.apptiorico.repositories;

import com.tiorico.apptiorico.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long>
{

}