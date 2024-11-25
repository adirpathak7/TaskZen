/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
// DOM content loader
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("first_name").addEventListener("input", function () {
        clearError("first_name");
    });
    document.getElementById("last_name").addEventListener("input", function () {
        clearError("last_name");
    });
    document.getElementById("email").addEventListener("input", function () {
        clearError("email");
    });
    document.getElementById("password").addEventListener("input", function () {
        clearError("password");
    });
    document.getElementById("confirm_password").addEventListener("input", function () {
        clearError("confirm_password");
    });
//    document.getElementById("client").addEventListener("input", function () {
//        clearError("role");
//    });
//    document.getElementById("freelancer").addEventListener("input", function () {
//        clearError("role");
//    });
});

// Clearing error's
function clearError(field) {
    document.getElementById("error-" + field).innerHTML = "";
}

// User Sign-Up validation's
function userSingUp(event) {
    event.preventDefault();

    var first_name = document.getElementById("first_name").value;
    var last_name = document.getElementById("last_name").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var confirm_password = document.getElementById("confirm_password").value;
    var mail = /^[a-zA-Z0-9.!#$%&'+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)$/;
    var strongPassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

    clearError("first_name");
    clearError("last_name");
    clearError("email");
    clearError("password");
    clearError("confirm_password");
    clearError("role");

    if (!first_name) {
        document.getElementById("error-first_name").innerHTML = "Please enter the First Name!";
        document.getElementById("first_name").focus();
        return false;
    }

    if (!last_name) {
        document.getElementById("error-last_name").innerHTML = "Please enter the Last Name!";
        document.getElementById("last_name").focus();
        return false;
    }

    if (!email) {
        document.getElementById("error-email").innerHTML = "Please enter the Email ID!";
        document.getElementById("email").focus();
        return false;
    }

    if (!email.match(mail)) {
        document.getElementById("error-email").innerHTML = "Invalid Email ID!";
        document.getElementById("email").focus();
        return false;
    }

    if (!password) {
        document.getElementById("error-password").innerHTML = "Please enter the Password!";
        document.getElementById("password").focus();
        return false;
    }

    if (!strongPassword.test(password)) {
        document.getElementById("error-password").innerHTML = "Weak Password! Please include:<br>At least 8 characters,<br>1 uppercase letter,<br>1 lowercase letter,<br>1 number,<br>and 1 special character.";
        document.getElementById("password").focus();
        return false;
    }

    if (!confirm_password) {
        document.getElementById("error-confirm_password").innerHTML = "Please enter the Confirm Password!";
        document.getElementById("confirm_password").focus();
        return false;
    }

    if (confirm_password !== password) {
        document.getElementById("error-confirm_password").innerHTML = "Password and Confirm password does not match!";
        document.getElementById("confirm_password").focus();
        return false;
    }

    var roleSelected = document.querySelector('input[name="role"]:checked');
    if (!roleSelected) {
        document.getElementById("error-role").innerHTML = "Please select the Role!";
        document.getElementById("client").focus();
        return false;
    }
//    alert("Registration Successful!");
//    return true;

    const userSignUpErrorMessage = document.getElementById('userSignUpErrorMessage');
    if (!userSignUpErrorMessage) {
        console.error("User Sign-Up message div not found!");
        return;
    }

    const signUpForm = document.getElementById('signUpForm');
    const signUpFormData = new FormData(signUpForm);

    fetch('http://localhost:8000/api/signUpUser', {
        method: 'POST',
        body: signUpFormData,
        credentials: 'include'
    }).then(response => response.json()).then(result => {
        if (result.data === "1") {
            alert("User registered successfully.");
//            userSignUpErrorMessage.innerHTML = '<p class="text-green-500">User registered successfully!</p>';
            console.log(result);
            signUpForm.reset();
//            window.location.href = "Login.xhtml";
        } else {
            userSignUpErrorMessage.innerHTML = '<p class="text-red-500">Error registering user. Please try again.</p>';
            console.log(result);
            signUpForm.reset();
        }
    }).catch((err) => {
        console.error("Error occurred while registering user!" + err);
        userSignUpErrorMessage.innerHTML = '<p class="text-red-500">Error occurred while registering user. Please try again.</p>';
        signUpForm.reset();
    });
}

// User Sign-In validation's
function userSignIn(event) {
    event.preventDefault();

    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var mail = /^[a-zA-Z0-9.!#$%&'+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)$/;

    clearError("email");
    clearError("password");

    if (!email) {
        document.getElementById("error-email").innerHTML = "Please enter the Email ID!";
        document.getElementById("email").focus();
        return false;
    }

    if (!email.match(mail)) {
        document.getElementById("error-email").innerHTML = "Invalid Email ID!";
        document.getElementById("email").focus();
        return false;
    }

    if (!password) {
        document.getElementById("error-password").innerHTML = "Please enter the Password!";
        document.getElementById("password").focus();
        return false;
    }

    const userSignInErrorMessage = document.getElementById('userSignInErrorMessage');
    if (!userSignInErrorMessage) {
        console.error("User Sign-In div not found!");
        return;
    }

    const signInForm = document.getElementById('signInForm');
    const signInFormData = new FormData(signInForm);

    fetch('http://localhost:8000/api/loginUser', {
        method: 'POST',
        body: signInFormData,
        credentials: 'include'
    }).then(response => response.json()).then(result => {
        if (result.data === "1") {
            alert("Login successfully.");
            if (result.role === "client") {
                localStorage.setItem('userRole', 'client');
//                userSignInErrorMessage.innerHTML = '<p class="text-green-500">Welcome, client.</p>';
//                console.log(result);
//                window.location.href = "Dashboard/UserDashboard.xhtml";
//                const forClient = document.getElementById('userIdentity');
//                forClient.innerHTML = 'Welcome Client..';
//                alert(forClient);
            } else {
                localStorage.setItem('userRole', 'freelancer');
//                userSignInErrorMessage.innerHTML = '<p class="text-green-500">Welcome, freelancer.</p>';
//                console.log(result);
//                window.location.href = "Dashboard/UserDashboard.xhtml";
//                const forCompany = document.getElementById('userIdentity');
//                forCompany.innerHTML = 'Welcome Company..';
//                alert(forCompany);
            }
            window.location.href = "Dashboard/Client/Maindashboard.xhtml";
            fetchUsers();
            signInForm.reset();
        } else {
            userSignInErrorMessage.innerHTML = '<p class="text-red-500">Invalid Password!</p>';
            console.log(result);
            signInForm.reset();
        }
    }).catch((err) => {
        console.error("Error occurred while user login! " + err);
        console.log(err);
        userSignInErrorMessage.innerHTML = '<p class="text-red-500">Error occurred while user Login. Please try again.</p>';
        signInForm.reset();
    });
}

function fetchUsers() {
    fetch('http://localhost:8000/api/getUsers').then(response => response.json()).then(data => {
        const usersData = document.getElementById('getUsers');
        usersData.innerHTML = '';

        data.forEach(user => {
            const newUser = document.createElement('p');
//            console.log(user);
            const newH1 = document.createElement('h1');
            newH1.innerHTML = "All User's";
            newUser.innerHTML = `
                    Id: ${user.user_id} <br>
                    First Name: ${user.first_name} <br>
                    Last Name: ${user.last_name} <br>
                    Email: ${user.email} <br>
                    Password: ${user.password} <br>
                    Role: ${user.role} <br> <br> <hr>
                `;
            usersData.appendChild(newUser);
        });
    }).catch(err => console.log('Error occure while fetching users! ', err));
}