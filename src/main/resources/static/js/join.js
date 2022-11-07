let validID = null;

function inputCheck() {
    if ($.trim($("#join-id").val()) == "") {
        alert("아이디를 입력해 주세요.");
        $("#join-id").val("").focus();
        return false;
    }
    if (validID == null || validID != $.trim($("#join-id").val())) {
        alert("아이디 중복확인 버튼을 클릭해 주세요.");
        $("#join-id").focus();
        return false;
    }
    if ($.trim($("#join-password").val()) == "") {
        alert("비밀번호를 입력해 주세요.");
        $("#join-password").val("").focus();
        return false;
    }

    if ($.trim($("#join-password-confirm").val()) == "") {
        alert("비밀번호 확인을 입력해 주세요.");
        $("#join-password-confirm").val("").focus();
        return false;
    }
    if ($.trim($("#join-name").val()) == "") {
        alert("이름을 입력해 주세요.");
        $("#join-name").val("").focus();
        return false;
    }
    if ($.trim($("#join-postcode").val()) == "") {
        alert("우편번호를 입력해 주세요.");
        $("#join-postcode").val("").focus();
        return false;
    }
    if ($.trim($("#join-address").val()) == "") {
        alert("주소를 입력해 주세요.");
        $("#join-address").val("").focus();
        return false;
    }
    if ($.trim($("#join-address-detail").val()) == "") {
        alert("상세주소를 입력해 주세요.");
        $("#join-address-detail").val("").focus();
        return false;
    }
    if ($.trim($("#join-contact").val()) == "") {
        alert("연락처를 입력해 주세요.");
        $("#join-contact").val("").focus();
        return false;
    }
    if ($.trim($("#join-email").val()) == "") {
        alert("이메일을 입력해 주세요.");
        $("#join-email").val("").focus();
        return false;
    }
};

function validateID() {
    validID = null;
    $("#id-message").hide();
    let id = $("#join-id").val();
    let text;

    let length = new RegExp(/^.{4,12}$/)
    let pattern = new RegExp(/(?=.*?[a-z0-9_])/)
    let special = new RegExp(/(?=.*?[?!@#$&])/)

    if (id.match(length) == null)
        text = '<p style="color: #ff6666">4 ~ 12자 범위내 입력해 주세요.</p>';
    else if (id.match(pattern) == null || id.match(special) != null)
        text = '<p style="color:#ff6666">소문자 또는 숫자로 입력해 주세요.</p>';
    else {
        $.ajax({
            async: false,
            type: 'POST',
            url: 'idCheck',
            data: {id: id},
            success: function (result) {
                if (result == 0) {
                    text = '<p style="color: #33cc33">사용 가능한 아이디입니다.</p>';
                    validID = id;
                } else {
                    text = '<p style="color: #ff6666">중복된 아이디가 존재합니다.</p>';
                    validID = null;
                }
            }
        });
    }

    $("#id-message").text('');
    $("#id-message").append(text);
    $("#id-message").show();
    if (validID == null)
        $("#join-id").val("").focus();

    return false;
};

function validatePassword() {
    $("#password-message").hide();
    let password = $("#join-password").val()
    let text;
    let flag;

    let length = new RegExp(/^.{8,16}$/)
    let pattern = new RegExp(/(?=.*?[a-zA-z])(?=.*?[0-9])/)
    let special = new RegExp(/(?=.*?[?!@#$&])/)

    if (password != "") {
        if (password.match(length) == null)
            text = '<p style="color: #ff6666">8 ~ 16자 범위내 입력해 주세요.</p>';
        else if (password.match(pattern) == null)
            text = '<p style="color: #ff6666">최소 1개 이상의 영어와 숫자를 포함해 주세요.</p>';
        else if (password.match(special) == null)
            text = '<p style="color: #ff6666">최소 1개 이상의 특수문자를 포함해 주세요.(?!@#$&)</p>';
        else {
            text = '<p style="color: #33cc33">사용 가능한 비밀번호 입니다.</p>';
            flag = true;
        }

        $("#password-message").text('');
        $("#password-message").append(text);
        $("#password-message").show();
        if (flag == true)
            $("#join-password-confirm").focus();
        else
            $("#join-password").val("").focus();
    }

    return false;
};

function confirmPassword() {
    $("#pw-message").hide();
    let password = $("#join-password")
    let confirm = $("#join-password-confirm")
    let text;

    if (password.val() != "" && confirm.val() != "") {
        if ($.trim(password.val()) != $.trim(confirm.val())) {
            text = '<p style="color: #ff6666">비밀번호 확인이 일치하지 않습니다.</p>';
            $(confirm).val("").focus();
        } else
            text = '<p style="color: #33cc33">비밀번호가 확인되었습니다.</p>';
    } else if (password.val() == "") {
        text = '<p style="color: #ff6666">비밀번호를 입력해주세요.</p>';
        $(confirm).val("");
    }

    $("#password-confirm-message").text('');
    $("#password-confirm-message").append(text);
    $("#password-confirm-message").show();
    return false;
};

function searchAddress() {
    new daum.Postcode({
        oncomplete: function (data) {
            document.getElementById('join-postcode').value = data.zonecode;
            document.getElementById('join-address').value = data.address;
        }
    }).open();
};

function inductSearch() {
    $("#address-message").hide();
    $("#detail-message").hide();
    let text = '<p style="color: #ff6666">우편번호 검색 버튼을 클릭해 주세요.</p>';

    let postcode = $("#join-postcode").val();
    let address = $("#join-address").val();
    let detail = $("#join-address-detail").val();

    if (postcode == "" && address != "") {
        $("#address-message").text('');
        $("#address-message").append(text);
        $("#address-message").show();
        $("#join-address").val("");
        $(postcode).focus();
        return false;
    }
    if (postcode == "" && detail != "") {
        $("#detail-message").text('');
        $("#detail-message").append(text);
        $("#detail-message").show();
        $("#join-address-detail").val("");
        $(postcode).focus();
        return false;
    }
};

function validateContact() {
    $("#contact-message").hide();
    let contact = $("#join-contact").val();
    let pattern = new RegExp(/^01(?:0|1|[6-9])-(?:\d{3}|\d{4})-\d{4}$/);

    if (contact != "" && contact.match(pattern) == null) {
        let text = '<p style="color: #ff6666">01 로 시작하고 하이픈을 포함하는 숫자만 입력해 주세요.</p>';
        $("#contact-message").text('');
        $("#contact-message").append(text);
        $("#contact-message").show();
        $("#join-contact").val("").focus();
        return false;
    }
};

function validateEmail() {
    $("#email-message").hide();
    let email = $("#join-email").val();
    let pattern = new RegExp(/^[a-zA-Z0-9+-\_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/);

    if (email != "" && email.match(pattern) == null) {
        let text = '<p style="color: #ff6666">유효하지 않는 이메일 양식입니다.</p>';
        $("#email-message").text('');
        $("#email-message").append(text);
        $("#email-message").show();
        $("#join-email").val("").focus();
        return false;
    }
};