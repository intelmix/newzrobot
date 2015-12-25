package com.intelmix.newzrobot.server;

import java.util.List;
import com.intelmix.newzrobot.server.data.*;

import org.springframework.data.repository.CrudRepository;

public interface GoogleUserRepository extends CrudRepository<GoogleUser, String> {
}

