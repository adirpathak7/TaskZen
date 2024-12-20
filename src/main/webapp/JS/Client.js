/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function clientProfileCreation(event) {
    event.preventDefault();

    const clientName = document.getElementById("client_name").value;
    const contact = document.getElementById("contact").value;
    const profilePictureInput = document.getElementById("profile_picture");
    const profilePicture = profilePictureInput.files.length > 0 ? profilePictureInput.files[0] : null;
    const country = document.getElementById("country").value;
    const establish = document.getElementById("establish").value;
    const industry = document.getElementById("industry").value;

    clearError("client_name");
    clearError("contact");
    clearError("profile_picture");
    clearError("country");
    clearError("establish");
    clearError("industry");

    if (!clientName) {
        document.getElementById("error-client_name").innerHTML = "Please enter the Client Name!";
        document.getElementById("client_name").focus();
        return false;
    }

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

    if (!establish) {
        document.getElementById("error-establish").innerHTML = "Please provide the Establishment Year!";
        document.getElementById("establish").focus();
        return false;
    }

    if (!industry) {
        document.getElementById("error-industry").innerHTML = "Please specify the Industry!";
        document.getElementById("industry").focus();
        return false;
    }


    const formData = new FormData();
    formData.append("client_name", clientName);
    formData.append("contact", contact);
    formData.append("profile_picture", profilePicture);
    formData.append("country", country);
    formData.append("establish", establish);
    formData.append("industry", industry);

    const apiUrl = "http://localhost:8000/api/client/clientsAllDetails";
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
            fetchClientDetails();
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

async function fetchClientDetails() {
    const apiUrl = "http://localhost:8000/api/client/getClientDetailsByToken";
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
//        console.log("result is : " + JSON.stringify(result));
//        console.log("result.data:", result.data);

        const client = result.data;
//        console.log("client data: ", client);

        if (client) {
            displayClientProfile(client);
        } else {
            console.error("No client data found.");
        }
    } catch (error) {
        console.error("Error fetching client details:", error);
    }
}

function displayClientProfile(client) {
    const show_profile_picture = document.getElementById("show_profile_picture");
    const showclient_name = document.getElementById("showclient_name");
    const showindustry = document.getElementById("showindustry");
    const showestablish = document.getElementById("showestablish");
    const showcountry = document.getElementById("showcountry");
    const showstatus = document.getElementById("showstatus");
    const showcontact = document.getElementById("showcontact");

    if (!show_profile_picture || !showclient_name || !showindustry || !showestablish || !showcountry || !showstatus || !showcontact) {
        console.log("One or more elements are missing.");
        return;
    }

    show_profile_picture.src = client.profile_picture || '';
    showclient_name.textContent = client.client_name || 'N/A';
    showindustry.textContent = `Industry: ${client.industry || 'N/A'}`;
    showestablish.textContent = `Established: ${client.establish || 'N/A'}`;
    showcountry.textContent = `Country: ${client.country || 'N/A'}`;
    showstatus.textContent = `Status: ${client.status || 'N/A'}`;
    showcontact.textContent = `Contact No.: ${client.contact || 'N/A'}`;
}


async function fetchClientProjectsDetails() {
    const apiUrl = "http://localhost:8000/api/client/getProjectsByClientId";
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
            throw new Error("Failed to fetch client project details");
        }

        const result = await response.json();
        const clientProjects = result.data;
//        console.log(clientProjects);

        if (clientProjects && Array.isArray(clientProjects)) {
            displayClientProjects(clientProjects);
        } else {
            console.error("No client project data found.");
        }
    } catch (error) {
        console.error("Error fetching client project details:", error);
    }
}

function displayClientProjects(clientProjects) {
    const projectsContainer = document.getElementById("client_projects_container");

    if (!projectsContainer) {
        console.log("Projects container element is missing.");
        return;
    }

    projectsContainer.innerHTML = "";

    clientProjects.forEach(project => {
//        console.log(project.client_project_id);
        const projectCard = document.createElement("div");
        projectCard.classList.add("bg-white", "p-8", "rounded-lg", "shadow-md", "mb-4");
        projectCard.style.display = "flex";
        projectCard.style.flexDirection = "column";
        projectCard.style.justifyContent = "center";
        projectCard.style.alignItems = "center";

        const projectImg = document.createElement("img");
        projectImg.src = project.project_picture;
        projectImg.classList.add("text-xl", "font-semibold", "text-red-900");
        projectImg.alt = "Not suported";

        const projectId = document.createElement("input");
        projectId.value = project.client_project_id;
        projectId.classList.add("text-xl", "font-semibold", "text-red-900");
        projectId.style.display = "none";

        const projectName = document.createElement("h2");
        projectName.textContent = project.client_project_name || 'N/A';
        projectName.classList.add("text-xl", "font-semibold", "hover:underline", "text-gray-800");

        const projectStatus = document.createElement("p");
        const statusText = document.createElement("span");
        statusText.textContent = project.status || 'N/A';
        statusText.style.color = project.status === 'pending' ? 'red' : 'gray';
        projectStatus.textContent = 'Status: ';
        projectStatus.appendChild(statusText);
        projectStatus.classList.add("text-sm", "text-gray-600");

        const projectDuration = document.createElement("p");
        projectDuration.textContent = `Duration: ${project.duration || 'N/A'}`;
        projectDuration.classList.add("text-sm", "text-gray-500");

        const rangeContainer = document.createElement("p");
        rangeContainer.textContent = `Range: ${project.minimum_range || 'N/A'} - ${project.maximum_range || 'N/A'}`;
        rangeContainer.classList.add("text-sm", "text-gray-500");

        const description = document.createElement("p");
        description.textContent = `Description: ${project.description || 'N/A'}`;
        description.classList.add("text-sm", "text-gray-600");

        const progressBarContainer = document.createElement("div");
        progressBarContainer.classList.add("w-full", "bg-gray-200", "h-2", "rounded-full", "mt-2");

        const progressFill = document.createElement("div");
        progressFill.classList.add("h-2", "rounded-full");

        let progressWidth = 0;
        let progressClass = "bg-gray-200";

        switch (project.status) {
            case 'pending':
                progressWidth = 0;
                progressClass = "bg-red-600";
                break;
            case 'inProgress':
                progressWidth = 40;
                progressClass = "bg-yellow-600";
                break;
            case 'accepted':
                progressWidth = 70;
                progressClass = "bg-blue-600";
                break;
            case 'completed':
                progressWidth = 100;
                progressClass = "bg-green-600";
                break;
        }

        progressFill.classList.add(progressClass);
        progressFill.style.width = `${progressWidth}%`;
        progressBarContainer.appendChild(progressFill);

        const buttonsContainer = document.createElement("div");
        buttonsContainer.classList.add("mt-2");

        const editButton = document.createElement("button");
        editButton.textContent = "Edit";
        editButton.classList.add("text-blue-600", "hover:underline", "mr-2");

        editButton.addEventListener("click", function (event) {
            event.preventDefault();
            openModal('editProjectModal');
            populateModalFields(project);
        });

        const deleteButton = document.createElement("button");
        deleteButton.textContent = "Delete";
        deleteButton.classList.add("text-red-600", "hover:underline");

        buttonsContainer.appendChild(editButton);
        buttonsContainer.appendChild(deleteButton);

        projectCard.appendChild(projectId);
        projectCard.appendChild(projectName);
        projectCard.appendChild(projectImg);
        projectCard.appendChild(projectStatus);
        projectCard.appendChild(projectDuration);
        projectCard.appendChild(rangeContainer);
        projectCard.appendChild(progressBarContainer);
        projectCard.appendChild(description);
        projectCard.appendChild(buttonsContainer);

        projectsContainer.appendChild(projectCard);
    });
}

document.addEventListener('DOMContentLoaded', function () {
    fetchClientDetails();
    fetchClientProjectsDetails();
});

function clientProjectPost(event) {
    event.preventDefault();

    const projectTitle = document.getElementById("client_project_name").value;
    const projectDetail = document.getElementById("description").value;
    const projectPicture = document.getElementById("project_picture").files[0];
    const projectDuration = document.getElementById("duration").value;
    const minimum_range = document.getElementById("minimum_range").value;
    const maximum_range = document.getElementById("maximum_range").value;

    clearError("client_project_name");
    clearError("description");
    clearError("project_picture");
    clearError("duration");
    clearError("minimum_range");
    clearError("maximum_range");


    if (!projectTitle) {
        document.getElementById("error-client_project_name").innerHTML = "Please enter the Project Title!";
        document.getElementById("client_project_name").focus();
        return false;
    }

    if (!projectDetail) {
        document.getElementById("error-description").innerHTML = "Please provide the Project Details!";
        document.getElementById("description").focus();
        return false;
    }

    if (!projectPicture) {
        document.getElementById("error-project_picture").innerHTML = "Please upload a Project Picture!";
        document.getElementById("project_picture").focus();
        return false;
    }

    if (!projectDuration) {
        document.getElementById("error-duration").innerHTML = "Please select the Project Duration!";
        document.getElementById("duration").focus();
        return false;
    }

    if (!minimum_range) {
        document.getElementById("error-minimum_range").innerHTML = "Please enter the Minimum range of project!";
        document.getElementById("minimum_range").focus();
        return false;
    }

    if (!maximum_range) {
        document.getElementById("error-maximum_range").innerHTML = "Please enter the Maximum range of project!";
        document.getElementById("maximum_range").focus();
        return false;
    }

    const formData = new FormData();
    formData.append("client_project_name", projectTitle);
    formData.append("description", projectDetail);
    formData.append("project_picture", projectPicture);
    formData.append("duration", projectDuration);
    formData.append("minimum_range", minimum_range);
    formData.append("maximum_range", maximum_range);

    const apiUrl = "http://localhost:8000/api/client/createClientProject";
    const token = sessionStorage.getItem("authToken");
//    console.log("Token used for API call: ", token);

    if (!token) {
        alert("Token is missing. Please login again.");
        return;
    }

    fetch(apiUrl, {
        method: "POST",
        body: formData,
        headers: {
            "Authorization": "Bearer " + token
        }
    }).then(response => response.json()).then(result => {
        if (result.data === "1") {

            alert("Project created successfully!");

            closeModal('newprojectModal');

        } else {
            alert("Failed to create project. Please try again.");
            console.error(result);
        }
    }).catch(error => {
        console.error("Error occurred while creating project: ", error);
        alert("An error occurred. Please try again.");
    });
}

function openModal(modalId) {
    const modal = document.getElementById(modalId);

    if (modal) {
        modal.classList.remove("hidden");
    } else {
        console.log("Modal with ID '" + modalId + "' not found!");
    }
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.add("hidden");
    }
}

function populateModalFields(project) {
    document.getElementById("client_project_name").value = project.client_project_name || "";
    document.getElementById("description").value = project.description || "";
    document.getElementById("duration").value = project.duration || "";
    document.getElementById("minimum_range").value = project.minimum_range || "";
    document.getElementById("maximum_range").value = project.maximum_range || "";
}

