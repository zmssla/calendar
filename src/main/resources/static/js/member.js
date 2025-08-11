function signupForm() {
    console.log('signupForm()');

    let form = document.signup_form;
    if (form.id.value === '') {
        alert('INPUT NEW USER ID!!');
        form.id.focus();

    } else if (form.pw.value === '') {
       alert('INPUT NEW USER PW!!');
       form.pw.focus();

    } else if (form.mail.value === '') {
        alert('INPUT NEW USER MAIL!!');
        form.mail.focus();

    } else if (form.phone.value === '') {
        alert('INPUT NEW USER PHONE!!');
        form.phone.focus();

    } else {
        form.submit();

    }

}

function signinForm() {
    console.log('signinForm()');

    let form = document.signin_form;
    if (form.id.value === '') {
        alert('INPUT USER ID!!');
        form.id.focus();

    } else if (form.pw.value === '') {
       alert('INPUT USER PW!!');
       form.pw.focus();

    } else {
        form.submit();

    }

}

function modifyForm() {
    console.log('modifyForm()');

    let form = document.modify_form;
    if (form.pw.value === '') {
       alert('INPUT USER PW!!');
       form.pw.focus();

    } else if (form.mail.value === '') {
        alert('INPUT USER MAIL!!');
        form.mail.focus();

    } else if (form.phone.value === '') {
        alert('INPUT USER PHONE!!');
        form.phone.focus();

    } else {
        form.submit();

    }

}

function findpasswordForm() {
    console.log('findpasswordForm()');

    let form = document.findpassword_form;
    if (form.id.value === '') {
        alert('INPUT MEMBER ID!!');
        form.id.focus();

    } else if (form.mail.value === '') {
        alert('INPUT MEMBER MAIL!!');
        form.mail.focus();

    } else {
        form.submit();
        
    }

}