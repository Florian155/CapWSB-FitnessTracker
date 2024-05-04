package com.capgemini.wsb.fitnesstracker.user.internal;

import jakarta.annotation.Nullable;
import jakarta.persistence.Id;

record SimpleUserDto(@Nullable Long Id, String firstName, String lastName) {

}