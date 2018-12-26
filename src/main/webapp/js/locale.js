function submit(selected) {
    if (selected != '' && selected != null) {
        $("#selectedLang")[0].setAttribute("value", selected);
        $('#changeLocaleForm').submit();
    }
}