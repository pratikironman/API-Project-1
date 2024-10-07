package com.ashu.blogapp;

import com.ashu.blogapp.Entity.Roles;
import com.ashu.blogapp.Repository.RolesRepo;
import com.ashu.blogapp.UtilClassHelper.AppConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner{


	public static void main(String[] args) {

		SpringApplication.run(BlogAppApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
//		return NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private RolesRepo rolesRepo;

	// runner method comes under "CommandLineRunner"
	@Override
	public void run(String... args) throws Exception{

		// this method is used to create role_tbl with 2 default values(Normal/admin) , if role_tbl is created or 1st time
		// or else if table already exist in DB then it wont be created
		// as we are not auto incrementing ID for roles as there has to be nly 2 types of roles
		try{
			//ADMIN
			Roles roles = new Roles();
			roles.setId(AppConstants.ADMIN_USER);
			roles.setName("ADMIN_USER");

			// NORMAL
			Roles roles1 = new Roles();
			roles1.setId(AppConstants.NORMAL_USER);
			roles1.setName("NORMAL_USER");

			List<Roles> rol = List.of(roles, roles1);

			List<Roles> result  = this.rolesRepo.saveAll(rol);

			result.forEach(r -> {
				System.out.println(r.getName());
			});

		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
