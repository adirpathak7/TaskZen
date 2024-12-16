/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
function clientProfileCreation(event) {
    event.preventDefault();
    // Collect form data
    const clientName = document.getElementById("client_name").value;
    const contact = document.getElementById("contact").value;
    const profilePictureInput = document.getElementById("profile_picture");
    const profilePicture = profilePictureInput.files.length > 0 ? profilePictureInput.files[0] : null;

    const country = document.getElementById("country").value;
//    console.log(contactNu);
    const establish = document.getElementById("establish").value;
    const industry = document.getElementById("industry").value;
    // Validation
//    clearError("client_name");
//    clearError("contactNu");
//    clearError("country");
//    clearError("establish");
//    clearError("industry");
//    if (!clientName) {
//        document.getElementById("error-client_name").innerHTML = "Please enter the Client Name!";
//        document.getElementById("client_name").focus();
//        return false;
//    }

//    if (!contactNu) {
//        document.getElementById("error-contactNu").innerHTML = "Please enter the Contact Number!";
//        document.getElementById("contactNu").focus();
//        return false;
//    }

//    if (!country1) {
//        document.getElementById("error-country1").innerHTML = "Please select the Country!";
//        document.getElementById("country1").focus();
//        return false;
//    }

//    if (!establish) {
//        document.getElementById("error-establish").innerHTML = "Please provide the Establishment Year!";
//        document.getElementById("establish1").focus();
//        return false;
//    }
//
//    if (!industry) {
//        document.getElementById("error-industry").innerHTML = "Please specify the Industry!";
//        document.getElementById("industry1").focus();
//        return false;
//    }

//    if (!profilePicture) {
//        document.getElementById("error-profile_picture").innerHTML = "Please upload a profile picture!";
//        profilePictureInput.focus();
//        return false;
//    }


// Create FormData object
    const formData = new FormData();
    formData.append("client_name", clientName);
    formData.append("contact", contact);
    formData.append("profile_picture", profilePicture);
    formData.append("country", country);
    formData.append("establish", establish);
    formData.append("industry", industry);
    // Set API endpoint and JWT token
    const apiUrl = "http://localhost:8000/api/clientsAllDetails";
    const token = sessionStorage.getItem("authToken"); // JWT token stored in sessionStorage

    // Make API request using fetch
    fetch(apiUrl, {
        method: "POST",
        body: formData,
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(response => response.json()).then(result => {
        if (result.data === "1") {
            alert("Profile updated successfully!");
            // Store updated JWT token and user role (if provided in response)
            if (result.token) {
                sessionStorage.setItem("authToken", result.token);
            }
            if (result.role) {
                sessionStorage.setItem("userRole", result.role);
            }

//            console.log(result);
            closeModal('editprofileModal');
        } else {
            alert("Failed to update profile. Please try again.");
            console.log(result);
        }
    }).catch(error => {
        console.error("Error occurred while updating profile: ", error);
        alert("An error occurred. Please try again.");
    });
}

// Clearing error's
function clearError(field) {
    document.getElementById("error-" + field).innerHTML = "";
}

// Function to open the modal
function openModal(modalId) {
    const modal = document.getElementById(modalId);
    modal.classList.remove('hidden');
    // Add event listener for Escape key
    document.addEventListener('keydown', function handleEscape(event) {
        if (event.key === 'Escape') {
            closeModal('editprofileModal');
            document.removeEventListener('keydown', handleEscape); // Remove listener after closing modal
        }
    });
}

// Function to close the modal
function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    modal.classList.add('hidden');
}

async function fetchClientDetails() {
    const apiUrl = "http://localhost:8000/api/getClientDetailsByToken";
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
            throw new Error("Failed to fetch client details");
        }

        const result = await response.json();
        const client = result.data[0];
        console.log("test client" + client);
        alert("test client" + client);
        displayProfile(client);
        displayContactDetails(client);
    } catch (error) {
        console.error("Error fetching client details:", error);
    }
}

function displayProfile(client) {
    const show_profile_picture = document.getElementById("show_profile_picture");
    const showclient_name = document.getElementById("showclient_name");
    const showindustry = document.getElementById("showindustry");
    const showestablish = document.getElementById("showestablish");
    const showcountry = document.getElementById("showcountry");
    const showstatus = document.getElementById("showstatus");
    const showcontact = document.getElementById("showcontact");
    show_profile_picture.innerHTML = `${client.show_profile_picture}`;
    showclient_name.innerHTML = `${client.showclient_name}`;
    showindustry.innerHTML = `${client.showindustry}`;
    showestablish.innerHTML = `${client.showestablish}`;
    showcountry.innerHTML = `${client.showcountry}`;
    showstatus.innerHTML = `${client.showstatus}`;
    showcontact.innerHTML = `${client.showcontact}`;
}

// Initialize data fetch
window.onload = fetchClientDetails;
