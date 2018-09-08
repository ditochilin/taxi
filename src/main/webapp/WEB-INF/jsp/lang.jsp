<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div style="float: right">
    <form name="changeLocaleForm" action="/Controller" method="post">
        <input type="hidden" name="command" value="changeLocale">
        <input type="hidden" name="fromURI" value="${pageContext.request.requestURI}">
        <select name='selectLangs' onchange="document.changeLocaleForm.submit()">
            <option style="background-image:url('/images/en.png');"
                    value="en_EN"  ${"en_EN" == sessionScope.locale ? 'selected' : ''}>English
            </option>
            <option style="background-image:url('/images/ru.png');"
                    value="ru_RU"  ${"ru_RU" == sessionScope.locale ? 'selected' : ''}>Russian
            </option>
        </select>
    </form>
</div>
<script language="JavaScript" type="text/javascript">
    function getSelectedValue(selected) {
        var index = selected.selectedIndex;
        if ((index >= 0) && (index < selected.length)) {
            return selected.options[index].value;
        }
        return '';
    }

    function submit(selected) {
        if (getSelectedValue(selected) != '') {
            selected.form.submit();
        }
    }
</script>