/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', function () {
    fetchClientDetails();
    fetchClientProjectsDetails();
    animateCounter(0, "totalClientProfit");
    animateCounter(0, "totalClientProject");
    fetchCounterInClientDashboard();
    const today = new Date().toISOString().split("T")[0]; // Get the current date in yyyy-mm-dd format
    document.getElementById("establish").setAttribute("max", today);
});


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

    const clientImage = document.getElementById("mainImageOfClient");
    const clientName = document.getElementById("mainNameOfClient");

    if (clientImage && clientName) {
        clientImage.src = client.profile_picture || '';
        clientName.innerHTML = `Welcome ${client.client_name}` || '';
    } else {
        console.error("Elements 'mainImageOfClient' or 'mainNameOfClient' not found.");
    }

}

let allProjects = [];
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

        if (clientProjects && Array.isArray(clientProjects)) {
            allProjects = clientProjects; // Save fetched data
            displayClientProjects(clientProjects);
        } else {
            console.error("No client project data found.");
        }
    } catch (error) {
        console.error("Error fetching client project details:", error);
    }
}
function filterProjects() {
    const searchInput = document.getElementById("search_input").value.toLowerCase();
    const statusFilter = document.getElementById("status_filter").value;
    const rangeMin = parseFloat(document.getElementById("range_min").value) || 0;
    const rangeMax = parseFloat(document.getElementById("range_max").value) || Infinity;

    const filteredProjects = allProjects.filter(project => {
        const matchesName = project.client_project_name.toLowerCase().includes(searchInput);
        const matchesStatus = !statusFilter || project.status === statusFilter;
        const matchesRange = (project.minimum_range >= rangeMin && project.maximum_range <= rangeMax);

        return matchesName && matchesStatus && matchesRange;
    });

    displayClientProjects(filteredProjects);
}


function displayClientProjects(clientProjects) {
    const projectsContainer = document.getElementById("client_projects_container");

    if (!projectsContainer) {
        console.log("Projects container element is missing.");
        return;
    }
    if (clientProjects.length === 0) {
        document.getElementById("noProjectMsg").innerHTML = "No projects found.";
    } else {
        document.getElementById("noProjectMsg").innerHTML = "";
    }
    projectsContainer.innerHTML = "";

    clientProjects.forEach(project => {
        const projectCard = document.createElement("div");
        projectCard.classList.add("p-6", "rounded-lg", "shadow-lg", "mb-6");
        projectCard.style.backgroundColor = "#fff";
        projectCard.style.border = "1px solid #e0e0e0";
        projectCard.style.display = "flex";
        projectCard.style.flexDirection = "column";
        projectCard.style.alignItems = "center";
        projectCard.style.textAlign = "center";
        projectCard.style.transition = "transform 0.3s, box-shadow 0.3s";

        projectCard.addEventListener("mouseenter", () => {
            projectCard.style.transform = "scale(1.05)";
            projectCard.style.boxShadow = "0 8px 16px rgba(0, 0, 0, 0.2)";
        });

        projectCard.addEventListener("mouseleave", () => {
            projectCard.style.transform = "scale(1)";
            projectCard.style.boxShadow = "0 4px 8px rgba(0, 0, 0, 0.1)";
        });

        const projectImg = document.createElement("img");
        projectImg.src = project.project_picture;
        projectImg.alt = "Project Image";
        projectImg.classList.add("w-64", "h-48", "rounded-md", "mb-4");
        projectImg.style.objectFit = "cover";
        projectImg.style.transition = "transform 0.3s";

        projectImg.addEventListener("mouseenter", () => {
            projectImg.style.transform = "scale(1.1)";
        });

        projectImg.addEventListener("mouseleave", () => {
            projectImg.style.transform = "scale(1)";
        });

        const projectName = document.createElement("h2");
        projectName.textContent = project.client_project_name || "N/A";
        projectName.style.fontSize = "1.25rem";
        projectName.style.fontWeight = "bold";
        projectName.style.color = "#333";
        projectName.style.marginBottom = "0.5rem";

        const projectStatus = document.createElement("p");
        projectStatus.innerHTML = `<strong>Status:</strong> <span style="color: ${
                project.status === "pending" ? "red" : "gray"
                };">${project.status || "N/A"}</span>`;
        projectStatus.style.marginBottom = "0.5rem";

        const projectDuration = document.createElement("p");
        projectDuration.textContent = `Duration: ${project.duration || "N/A"}`;
        projectDuration.style.marginBottom = "0.5rem";

        const rangeContainer = document.createElement("p");
        rangeContainer.textContent = `Range: ${project.minimum_range || "N/A"} - ${project.maximum_range || "N/A"}`;
        rangeContainer.style.marginBottom = "0.5rem";

        const description = document.createElement("p");
        description.textContent = `Description: ${project.description || "N/A"}`;
        description.style.color = "#555";
        description.style.marginBottom = "1rem";

        const progressBarContainer = document.createElement("div");
        progressBarContainer.classList.add("w-full", "bg-gray-200", "h-2", "rounded-full", "mb-4");

        const progressFill = document.createElement("div");
        progressFill.classList.add("h-2", "rounded-full");
        let progressWidth = 0;
        let progressClass = "bg-gray-200";

        switch (project.status) {
            case "pending":
                progressWidth = 0;
                progressClass = "bg-red-600";
                break;
            case "inProgress":
                progressWidth = 40;
                progressClass = "bg-yellow-600";
                break;
            case "halfCompleted":
                progressWidth = 70;
                progressClass = "bg-blue-600";
                break;
            case "completed":
                progressWidth = 100;
                progressClass = "bg-green-600";
                break;
        }

        progressFill.classList.add(progressClass);
        progressFill.style.width = `${progressWidth}%`;
        progressBarContainer.appendChild(progressFill);

        const buttonsContainer = document.createElement("div");
        buttonsContainer.style.display = "flex";
        buttonsContainer.style.justifyContent = "center";
        buttonsContainer.style.gap = "1rem";

        const editButton = document.createElement("button");
        editButton.innerHTML = '<i class="fas fa-edit"></i>';
        editButton.title = "Edit";
        editButton.classList.add("text-blue-600", "hover:underline");

        editButton.addEventListener("click", (event) => {
            event.preventDefault();
            openModal("editProjectModal");
            populateModalFields(project);
        });

        const deleteButton = document.createElement("i");
        deleteButton.classList.add("ri-delete-bin-6-fill", "text-2xl", "hover:text-red-700", "transition", "duration-300", "cursor-pointer");

        deleteButton.addEventListener("click", function (event) {
            event.preventDefault();

            const userConfirmed = window.confirm("Are you sure you want to delete this project?");

            if (userConfirmed) {
                const projectId = project.client_project_id;
                console.log(projectId);
                fetch(`http://localhost:8000/api/client/updateStatusRemove/${projectId}`, {
                    method: "PUT"
                })
                        .then(response => response.json())
                        .then(data => {
                            if (data.success) {
                                alert("Project successfully deleted.");
                                projectCard.remove();
                            } else {
                                alert("There was an issue deleting the project.");
                            }
                        })
                        .catch(error => {
                            console.error("Error deleting project:", error);
//                            alert("An error occurred while deleting the project.");
                        });
            }
        });


        buttonsContainer.appendChild(editButton);
        buttonsContainer.appendChild(deleteButton);

        projectCard.appendChild(projectImg);
        projectCard.appendChild(projectName);
        projectCard.appendChild(projectStatus);
        projectCard.appendChild(projectDuration);
        projectCard.appendChild(rangeContainer);
        projectCard.appendChild(description);
        projectCard.appendChild(progressBarContainer);
        projectCard.appendChild(buttonsContainer);

        projectsContainer.appendChild(projectCard);
    });
}


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
            document.querySelectorAll('form').reset;
        } else {
//            alert("Failed to create project. Please try again.");
            alert(result.error);
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

function animateCounter(target, counterElementId) {
    let currentCount = 0;
    const counterElement = document.getElementById(counterElementId);

    if (!counterElement) {
        console.error(`Element with ID '${counterElementId}' not found.`);
        return;
    }

    const increment = target / 100;
    const interval = setInterval(() => {
        currentCount += increment;
        if (currentCount >= target) {
            currentCount = target;
            clearInterval(interval);
        }
        counterElement.innerHTML = Math.floor(currentCount);
    }, 25);
}


async function fetchCounterInClientDashboard() {
    const apiUrls = [
        "http://localhost:8000/api/client/countAndSumProjects",
        "http://localhost:8000/api/freelancer/appliedProjects/sumRanges",
        "http://localhost:8000/api/freelancer/appliedProjects/count"
    ];

    const token123 = sessionStorage.getItem("authToken");

    if (!token123) {
        console.error("Token not found in sessionStorage.");
        return;
    }

    try {
        const [clientDataResponse, sumRangeResponse, countResponse] = await Promise.all(
                apiUrls.map(url =>
                    fetch(url, {
                        method: "GET",
                        headers: {
                            "Authorization": `Bearer ${token123}`,
                            "Content-Type": "application/json"
                        }
                    })
                )
                );

        if (!clientDataResponse.ok || !sumRangeResponse.ok || !countResponse.ok) {
            const errorData = await clientDataResponse.json();
            throw new Error(`Failed to fetch data: ${errorData.error || clientDataResponse.statusText}`);
        }

        const clientData = await clientDataResponse.json();
        const sumRangeData = await sumRangeResponse.json();
        const countData = await countResponse.json();

        if (clientData && sumRangeData && countData) {
            document.getElementById("totalClientProject").innerHTML = clientData.projectCount || 0;
            const totalSumRange = document.getElementById("totalClientSumRange").innerHTML = clientData.totalRangeSum || 0;

            const firstProfit = document.getElementById("totalClientProfit").innerHTML = sumRangeData || 0;
            document.getElementById("totalClientFreelancer").innerHTML = countData || 0;
            const orignalProfit = totalSumRange - firstProfit;
            animateCounter(clientData.totalRangeSum, "totalClientSumRange");
            animateCounter(clientData.projectCount, "totalClientProject");

            animateCounter(orignalProfit, "totalClientProfit");
            animateCounter(countData, "totalClientFreelancer");
        } else {
            console.error("No data found for the client or freelancer.");
        }
    } catch (error) {
        console.error("Error fetching client and freelancer data:", error.message);
    }
}


function populateModalFields(project) {
    const modalFields = {
        projectId: document.getElementById("editProjectId"),
        projectName: document.getElementById("editProjectName"),
        projectDuration: document.getElementById("editProjectDuration"),
        minimumRange: document.getElementById("editMinimumRange"),
        maximumRange: document.getElementById("editMaximumRange"),
        description: document.getElementById("editDescription"),
        projectPicture: document.getElementById("editProjectPicture")
    };

    modalFields.projectId.value = project.client_project_id || "";
    modalFields.projectName.value = project.client_project_name || "";
    modalFields.projectDuration.value = project.duration || "";
    modalFields.minimumRange.value = project.minimum_range || "";
    modalFields.maximumRange.value = project.maximum_range || "";
    modalFields.description.value = project.description || "";
    modalFields.projectPicture.value = project.project_picture || "";
}


function editClientAddedProject(event) {
    const projectId = document.getElementById("editProjectId").value;
    // Create a FormData object to hold the form fields
    const formData = new FormData();
    formData.append("client_project_name", document.getElementById("editProjectName").value);
    formData.append("description", document.getElementById("editDescription").value);
    formData.append("duration", document.getElementById("editProjectDuration").value);
    formData.append("minimum_range", document.getElementById("editMinimumRange").value);
    formData.append("maximum_range", document.getElementById("editMaximumRange").value);

    // If the user uploads a project picture, add it to FormData
    const projectPictureInput = document.getElementById("editProjectPicture");
    if (projectPictureInput.files && projectPictureInput.files[0]) {
        formData.append("project_picture", projectPictureInput.files[0]);
    }

    fetch(`http://localhost:8000/api/client/updateProjectDetail/${projectId}`, {
        method: "PUT",
        body: formData
    })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to update project. Status: " + response.status);
                }
                return response.json();
            })
            .then(data => {
                alert("Project updated successfully:");
                closeModal("editProjectModal");
            })
            .catch(error => {
                console.error("Error updating project:", error);
            });
}
