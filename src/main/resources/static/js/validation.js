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