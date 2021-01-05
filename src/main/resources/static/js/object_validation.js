function validateObjectForm(form) {
	let name = form.name.value;
	let description = form.description.value;

    let valid = true;

	if (!(isPresented(name, "Name", "name"))) {
		valid = false;
	} else if (name.length < 5) {
        showWarning("Name must contain at least 5 symbols", "name");
        valid = false;
    }

    return valid
}