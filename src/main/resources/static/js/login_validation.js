function validateLoginForm(form) {
	let username = form.txtUsername.value;
	let password = form.txtPassword.value;

    let valid = true;

	if (!(isPresented(username, "Username", "username"))) {
		valid = false;
    }

	if (!(isPresented(password, "Password", "password"))) {
		valid = false;
    }

    return valid
}