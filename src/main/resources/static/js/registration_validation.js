const email_regexp = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i
const phone_regexp = /^((8|\+7)[\- ]?)?(\(?\d{3}\)?[\- ]?)?[\d\- ]{7,10}$/

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
	} else if (!email_regexp.test(String(email).toLowerCase())) {
        showWarning("Pass valid email", "email");
		valid = false;
	}

	if (!(isPresented(telephone, "Telephone", "telephone"))) {
		valid = false;
	} else if (!phone_regexp.test(String(telephone).toLowerCase())) {
        showWarning("Pass valid phone number", "telephone");
        valid = false;
    }

	if (!(isPresented(dormitory, "Dormitory", "dormitory"))) {
		valid = false;
	}

    return valid
}