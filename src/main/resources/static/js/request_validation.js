function validateRequestForm(form) {
	let description = form.description.value;
	let select = form.select.value;
    let object;
    if (form.objectId.length || form.objectId.checked) {
        object = form.objectId.value;
    }
    let service;
    if (form.serviceId.length || form.serviceId.checked) {
        service = form.serviceId.value;
    }

    let valid = true;

    if (select == "selectObject") {
    	if (!(isPresented(object, "Object", "object"))) {
    		valid = false;
    	}
    } else if (select == "selectService") {
        if (!(isPresented(service, "Service", "service"))) {
            valid = false;
        }
    }

    return valid
}