/*
(() => {
    const currentpassword = document.querySelector('#current-password');
    const newpassword = document.querySelector('#new-password');
    const confirmpassword = document.querySelector('#confirm-password');
    const errMsg = document.querySelector('#errMsg');
    const btnsubmit = document.querySelector('#btn-submit');
    init();

    function init() {
        btnsubmit.addEventListener('click', () => {
    console.log("測試function有點到btn");
});
    }

})();
*/
// ------------------------------------------------------------------------------------------------
(() => {
    const btn1 = document.querySelector('#btn-submit');
    const oPassword = document.querySelector('#current-password');
    const nPassword = document.querySelector('#new-password');
    const confirmPassword = document.querySelector('#confirm-password');
    const msg = document.querySelector('#msg');
    init();

    function init() {
        btn1.addEventListener('click', edit);

        /*
        fetch('getInfo', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
        })
            .then(resp => resp.json())
            .then(body => {
                username.value = body['username'];
                //nickname.value = body['nickname'];
            });
            */

        oPassword.addEventListener('blur', checkOldPassword);
    }

    function checkOldPassword() {
        fetch('checkPassword', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ password: oPassword.value })
        })
            .then(resp => resp.json())
            .then(body => {
                btn1.disabled = !body['successful']
            });
    }

    function edit() {
        if (nPassword.value && confirmPassword.value) {
            if (nPassword.value.length < 6 || nPassword.value.length > 12) {
                msg.textContent = '密碼長度須介於6~12字元';
                return;
            }

            if (confirmPassword.value !== nPassword.value) {
                msg.textContent = '密碼與確認密碼不相符';
                return;
            }
        }

        msg.textContent = '';

        fetch('edit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                password: nPassword.value
                // ,nickname: nickname.value
            }),
        })
            .then(resp => resp.json())
            .then(body => {
                const { successful, message } = body;
                if (successful) {
                    msg.className = 'info';
                    oPassword.value = '';
                    nPassword.value = '';
                    confirmPassword.value = '';
                    btn1.disabled = true;
                } else {
                    msg.className = 'error';
                }
                msg.textContent = message;
            });
    }
})();
