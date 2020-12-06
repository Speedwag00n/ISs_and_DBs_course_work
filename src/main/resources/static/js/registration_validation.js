function validateRegistrationForm(form) {
	let username = form.username.value;
	let password = form.password.value;
    let password2 = form.password2.value;
    let name = form.name.value;
	let surname = form.surname.value;
    let email = form.email.value;
    let telephone = form.telephone.value;
    let dormitory = form.dormitory.value;

    let valid = true;

	if (!(isPresented(username, "Username", "username"))) {
		valid = false;
	} else if (username.length < 5) {
        showWarning("Username must contain at least 5 symbols", "username");
		valid = false;
	}

	if (!(isPresented(password, "Password", "password"))) {
		valid = false;
	} else if (password.length < 6) {
        showWarning("Password must contain at least 5 symbols", "password");
		valid = false;
	}

	if (!(isPresented(password2, "Repeat password", "password2"))) {
		valid = false;
	} else if (password != password2) {
        showWarning("Passwords aren't same. Please, check inputted data", "password2");
		valid = false;
	}

    if (!(isPresented(name, "Name", "name"))) {
        valid = false;
    }

    if (!(isPresented(surname, "Surname", "surname"))) {
        valid = false;
    }

	if (!(isPresented(email, "Email", "email"))) {
		valid = false;
	}

	if (!(isPresented(telephone, "Telephone", "telephone"))) {
		valid = false;
	}

	if (!(isPresented(dormitory, "Dormitory", "dormitory"))) {
		valid = false;
	}

    return valid
}

function isPresented(param, paramName, warningName) {
	if (param == "" || param == null) {
		showWarning("Field '" + paramName + "' can't be blank", warningName);
		return false;
	} else {
		showWarning("", warningName);
	}

	return true;
}

function isNumber(param, paramName, warningName) {
    if (!(!isNaN( Number(accuracy) ) && accuracy.lastIndexOf('.') != (accuracy.length - 1))) {
		showWarning("Value '" + paramName + "' must be number", warningName);
		return false;
    } else {
        showWarning("", warningName);
    }

    return true;
}

function showWarning(warningMessage, warningName) {
	let warningContainer = document.getElementById("warning-container-" + warningName);

	if (warningContainer != null) {
		warningContainer.textContent = warningMessage;
	}
}