/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
function freelancerProfileCreation(event) {
//    alert(1);
    event.preventDefault();
    const contact = document.getElementById("contact").value;
    const profilePictureInput = document.getElementById("profile_picture");
    const profilePicture = profilePictureInput.files.length > 0 ? profilePictureInput.files[0] : null;
    const country = document.getElementById("country").value;
    const dob = document.getElementById("dob").value;
    const gender = document.querySelector('input[name="gender"]:checked')?.value;
    const github_link = document.getElementById("github_link").value;
    const linkedin_link = document.getElementById("linkedin_link").value;
    const portfolio_link = document.getElementById("portfolio_link").value;
    clearError("contact");
    clearError("profile_picture");
    clearError("country");
    clearError("dob");
    clearError("gender");
    if (!contact) {
        document.getElementById("error-contact").innerHTML = "Please enter the Contact Number!";
        document.getElementById("contact").focus();
        return false;
    }

    if (!profilePicture) {
        document.getElementById("error-profile_picture").innerHTML = "Please upload a profile picture!";
        profilePictureInput.focus();
        return false;
    }

    if (!country) {
        document.getElementById("error-country").innerHTML = "Please select the Country!";
        document.getElementById("country").focus();
        return false;
    }

    if (!dob) {
        document.getElementById("error-dob").innerHTML = "Please enter your Date of Birth!";
        document.getElementById("dob").focus();
        return false;
    }

    if (!gender) {
        document.getElementById("error-gender").innerHTML = "Please specify the Industry!";
        document.getElementById("gender").focus();
        return false;
    }


    const formData = new FormData();
    formData.append("contact", contact);
    formData.append("profile_picture", profilePicture);
    formData.append("country", country);
    formData.append("dob", dob);
    formData.append("gender", gender);
    formData.append("github_link", github_link);
    formData.append("linkedin_link", linkedin_link);
    formData.append("portfolio_link", portfolio_link);
    const apiUrl = "http://localhost:8000/api/freelancer/freelancersAllDetails";
    const token = sessionStorage.getItem("authToken");
    fetch(apiUrl, {
        method: "POST",
        body: formData,
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(response => response.json()).then(result => {
        if (result.data === "1") {
            alert("Profile updated successfully!");
            if (result.token) {
                sessionStorage.setItem("authToken", result.token);
            }
            if (result.role) {
                sessionStorage.setItem("userRole", result.role);
            }

            closeModal('editprofileModal');
            fetchFreelancerDetails();
        } else {
            alert("Failed to update profile. Please try again.");
//            console.log(result);
        }
    }).catch(error => {
        console.error("Error occurred while updating profile: ", error);
        alert("An error occurred. Please try again.");
    });
}

function clearError(field) {
    document.getElementById("error-" + field).innerHTML = "";
}

function openModal(modalId) {
    const modal = document.getElementById(modalId);
    modal.classList.remove('hidden');
    document.addEventListener('keydown', function handleEscape(event) {
        if (event.key === 'Escape') {
            closeModal('editprofileModal');
            document.removeEventListener('keydown', handleEscape);
        }
    });
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    modal.classList.add('hidden');
}


async function fetchFreelancerDetails() {
    const apiUrl = "http://localhost:8000/api/freelancer/getFreelancerDetailsByToken";
    const token = sessionStorage.getItem("authToken");
    try {
        const response = await fetch(apiUrl, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            }
        });
        if (!response.ok) {
            throw new Error("Failed to fetch freelancer details");
        }

        const result = await response.json();
//        console.log("result is : " + JSON.stringify(result));
//        console.log("result.data:", result.data);
        const freelancer = result.data;

        if (freelancer) {
            displayProfile(freelancer);
        } else {
            console.error("No freelancer data found.");
        }
    } catch (error) {
        console.error("Error fetching freelancer details:", error);
    }
}

function displayProfile(freelancer) {
    const show_profile_picture = document.getElementById("show_profile_picture");
//        const showfreelancer_name = document.getElementById("show_freelancer_name");
    const show_contact = document.getElementById("show_contact");
    const show_dob = document.getElementById("show_dob");
    const show_gender = document.getElementById("show_gender");
    const show_country = document.getElementById("show_country");
    const show_status = document.getElementById("show_status");
    const show_github_link = document.getElementById("show_github_link");
    const show_linkedin_link = document.getElementById("show_linkedin_link");
    const show_portfolio_link = document.getElementById("show_portfolio_link");
    if (!show_profile_picture || !show_dob || !show_gender || !show_country || !show_status || !show_contact) {
        console.log("One or more elements are missing.");
        return;
    }

    show_profile_picture.src = freelancer.profile_picture || '';
//    showfreelancer_name.textContent = freelancer.freelancer_name || 'N/A';
    show_dob.textContent = `Dob: ${freelancer.dob || 'N/A'}`;
    show_gender.textContent = `Gender: ${freelancer.gender || 'N/A'}`;
    show_country.textContent = `Country: ${freelancer.country || 'N/A'}`;
    show_status.textContent = `Status: ${freelancer.status || 'N/A'}`;
    show_contact.textContent = `Contact No.: ${freelancer.contact || 'N/A'}`;
    show_github_link.href = freelancer.github_link;
    show_linkedin_link.href = freelancer.linkedin_link;
    show_portfolio_link.href = freelancer.portfolio_link;

}

document.addEventListener('DOMContentLoaded', function () {
    fetchFreelancerDetails();
});
