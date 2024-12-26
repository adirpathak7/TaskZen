/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', function () {
    fetchFreelancerDetails();
    fetchClientsProjectsDetails();
    fetchFreelancerDashboardData();
    fetchFreelaancerProjectsDetailsatdashboard();
});

function freelancerProfileCreation(event) {
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


async function fetchClientsProjectsDetails() {
    const apiUrl = "http://localhost:8000/api/client/getAllClientsProjects";
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
        const freelancerProjects = result;
//        console.log(freelancerProjects);


        if (freelancerProjects && Array.isArray(freelancerProjects)) {
            displayFreelancerProjects(freelancerProjects);
        } else {
            console.error("No client project data found.");
        }
    } catch (error) {
        console.error("Error fetching client project details:", error);
    }
}

function displayFreelancerProjects(freelancerProjects) {
    const projectsContainer = document.getElementById("freelancer_projects_container");
    if (!projectsContainer) {
        console.log("Projects container element is missing.");
        return;
    }

    projectsContainer.innerHTML = "";
    freelancerProjects.forEach(project => {

        const clientDetails = project.client_id || {};
        const clientId = clientDetails.client_id || "N/A";
        const clientName = clientDetails.client_name || "N/A";
        const clientContact = clientDetails.contact || "N/A";
        const clientCountry = clientDetails.country || "N/A";
        const clientEstablish = clientDetails.establish || "N/A";
        const clientIndustry = clientDetails.industry || "N/A";
        const clientProfilePicture = clientDetails.profile_picture || "default-profile.jpg";
        const projectPicture = project.project_picture || "default-project.jpg";
        const projectMinimumRange = project.minimum_range;
        const projectMaximumRange = project.maximum_range;
        const projectCard = document.createElement("div");
        projectCard.classList.add("bg-white", "p-5", "rounded-lg", "shadow-md", "mb-4");
        // Project ID (hidden)
        const projectId = document.createElement("input");
        projectId.value = project.client_project_id || "N/A";
        projectId.id = "client_project_id";
        projectId.classList.add("text-xl", "font-semibold", "text-red-900");
        projectId.style.display = "none";
        // Project Name
        const projectName = document.createElement("h2");
        projectName.textContent = project.client_project_name || "N/A";
        projectName.classList.add("text-xl", "font-semibold", "text-gray-800");
        // Client Name
        const clientNameEl = document.createElement("p");
        clientNameEl.textContent = `Client Name: ${clientName}`;
        clientNameEl.classList.add("text-lg", "font-semibold", "text-gray-800");
        // Project Duration
        const projectDuration = document.createElement("p");
        projectDuration.textContent = `Duration: ${project.duration || 'N/A'}`;
        projectDuration.classList.add("text-sm", "text-gray-500");
        // Project Range
        const projectRangeEl = document.createElement("p");
        const projectRangeEl1 = document.createElement("p");
        projectRangeEl.textContent = `Project Range: ${projectMinimumRange} - ${projectMaximumRange} Rs.`;
        projectRangeEl.id = "project-minimum-range";
        projectRangeEl1.id = "project-maximum-range";
        projectRangeEl.classList.add("text-sm", "text-gray-500");
        // Project Image
        const projectImg = document.createElement("img");
        projectImg.src = projectPicture;
        projectImg.alt = "Project picture not supported";
        projectImg.classList.add("w-20", "h-20");
        // View More Button
        const viewButton = document.createElement("button");
        viewButton.type = "button";
        viewButton.textContent = "View More";
        viewButton.classList.add("text-blue-600", "hover:text-blue-800");
        viewButton.addEventListener("click", (event) => {
            event.preventDefault();
            const modalDetails = document.getElementById("client-details");
            if (modalDetails) {
                modalDetails.innerHTML = `
                    <strong>Client Name:</strong> ${clientName}<br>
                    <strong>Contact:</strong> ${clientContact}<br>
                    <strong>Country:</strong> ${clientCountry}<br>
                    <strong>Established:</strong> ${clientEstablish}<br>
                    <strong>Industry:</strong> ${clientIndustry}<br>
                    <input type="hidden" id="client_id" value="${clientId}"/>
                    <img src="${clientProfilePicture}" alt="Client Profile Picture" class="w-20 h-20 rounded-full mb-4"><br>
                    <strong>Project Description:</strong> ${project.description || "N/A"}<br>
                    <strong>Project Range:</strong> ${projectMinimumRange} - ${projectMaximumRange} Rs.<br>
                `;
            }

            // Store the project ranges in the form
            document.getElementById("project-minimum-range").value = project.minimum_range;
            document.getElementById("project-maximum-range").value = project.maximum_range;
            document.getElementById("client_project_id").value = project.client_project_id;
            document.getElementById("client_id").value = clientId;
            openModal("freelancerOpendProject");
        });
        projectCard.appendChild(projectId);
        projectCard.appendChild(projectName);
        projectCard.appendChild(clientNameEl);
        projectCard.appendChild(projectDuration);
        projectCard.appendChild(projectRangeEl);
        projectCard.appendChild(projectRangeEl1);
        projectCard.appendChild(projectImg);
        projectCard.appendChild(viewButton);
        projectsContainer.appendChild(projectCard);
    });
}


function freelanverApplyForProject(event) {
    event.preventDefault();
    // Get freelancer input values
    const freelancer_range = parseFloat(document.getElementById("freelancer-range").value);
    const freelancer_description = document.getElementById("freelancer-description").value;
    const client_project_id = document.getElementById("client_project_id").value;
    const client_id = document.getElementById("client_id").value;
    const duration = document.getElementById("duration").value;
    // Get project range values from the hidden input fields
    const project_minimum_range = parseFloat(document.getElementById("project-minimum-range").value);
    const project_maximum_range = parseFloat(document.getElementById("project-maximum-range").value);
    // Clear any previous error messages
    clearError("freelancer-range");
    clearError("freelancer-description");
    clearError("duration");
    // Range validation
    if (!freelancer_range) {
        document.getElementById("error-freelancer-range").innerHTML = "Please enter a valid range!";
        document.getElementById("freelancer-range").focus();
        return false;
    }

    if (freelancer_range < project_minimum_range || freelancer_range > project_maximum_range) {
        document.getElementById("error-freelancer-range").innerHTML = `Freelancer range should be between ${project_minimum_range} and ${project_maximum_range} Rs.`;
        document.getElementById("freelancer-range").focus();
        return false;
    }

    if (!freelancer_description) {
        document.getElementById("error-freelancer-description").innerHTML = "Please provide a description!";
        document.getElementById("freelancer-description").focus();
        return false;
    }

    if (!duration) {
        document.getElementById("error-duration").innerHTML = "Please select the project duration!";
        document.getElementById("duration").focus();
        return false;
    }

    var formData = new FormData();
    formData.append("freelancer_range", freelancer_range);
    formData.append("freelancer_description", freelancer_description);
    formData.append("client_project_id", client_project_id);
    formData.append("client_id", client_id);
    formData.append("duration", duration);

    const apiUrl = "http://localhost:8000/api/freelancer/applyProjects";
    const token = sessionStorage.getItem("authToken");
    fetch(apiUrl, {
        method: "POST",
        body: formData,
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }).then(response => response.json()).then(result => {

        if (result.message === "not") {
            alert("You have already applied for this project!");
            document.querySelector('form').reset();
            fetchFreelancerDetails();
            closeModal("freelancerOpendProject");
        } else {

            if (result.data === "1") {
                alert("Application applied successfully!");
                if (result.token) {
                    sessionStorage.setItem("authToken", result.token);
                }
                if (result.role) {
                    sessionStorage.setItem("userRole", result.role);
                }
                document.querySelectorAll('form').reset;
                fetchFreelancerDetails();
                closeModal("freelancerOpendProject");
            } else {
                alert("Failed to apply project. Please try again.");
            }
        }
    }).catch(error => {
        console.error("Error occurred while applied project: ", error);
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

async function fetchFreelancerDashboardData() {
    const apiUrls = [
        "http://localhost:8000/api/freelancer/count/completed",
        "http://localhost:8000/api/freelancer/count/pending",
        "http://localhost:8000/api/freelancer/count/completed-status",
        "http://localhost:8000/api/freelancer/count/freelancer-range"
    ];

    const authToken = sessionStorage.getItem("authToken");

    if (!authToken) {
        console.error("Token not found in sessionStorage.");
        return;
    }

    try {
        // Fetch data from all APIs concurrently
        const [completedResponse, pendingResponse, completedStatusResponse, freelancerRangeResponse] = await Promise.all(
                apiUrls.map(url =>
                    fetch(url, {
                        method: "GET",
                        headers: {
                            "Authorization": `Bearer ${authToken}`,
                            "Content-Type": "application/json"
                        }
                    })
                )
                );

        // Handle non-OK responses
        if (!completedResponse.ok || !pendingResponse.ok || !completedStatusResponse.ok || !freelancerRangeResponse.ok) {
            throw new Error("Failed to fetch data from one or more APIs.");
        }
        console.log(document.getElementById("totalMyProject"));

        // Parse JSON responses
        const completedData = await completedResponse.json();
        const pendingData = await pendingResponse.json();
        const completedStatusData = await completedStatusResponse.json();
        const freelancerRangeData = await freelancerRangeResponse.json();

        // Update DOM and animate counters
        animateCounter(completedData || 0, "totalMyProject");
        animateCounter(freelancerRangeData.totalEarnings || 0, "totalMyEarnings"); // Assuming range data includes total earnings
        animateCounter(completedStatusData || 0, "totalCompleteProject");
        animateCounter(pendingData || 0, "totalPendingProject");
    } catch (error) {
        console.error("Error fetching freelancer dashboard data:", error.message);
    }
}



async function fetchFreelaancerProjectsDetailsatdashboard() {
    const apiUrl = "http://localhost:8000/api/freelancer/getFreelancerAppliedPostByToken";
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
            throw new Error("Failed to fetch freelancer project details!");
        }

        const result = await response.json();
        const freelancerProjects = result.data;
//        console.log(freelancerProjects);

        if (freelancerProjects && Array.isArray(freelancerProjects)) {
            displayFreelancerApplyProjectsatDashboard(freelancerProjects);
        } else {
//            console.error("No freelancer project data found.");
        }
    } catch (error) {
        console.error("Error fetching freelancer project details:", error);
    }
}

function displayFreelancerApplyProjectsatDashboard(freelancerProjects) {
    const tableBody = document.getElementById('displayFreelancerApplyProjectsalldashboard-table-body');
    tableBody.innerHTML = '';

    freelancerProjects.forEach(data => {
        const clientName = data.client_id.client_name;
        const clientProjectTitle = data.clientProject.client_project_name;
        const clientProjectRange = `${data.clientProject.minimum_range} - ${data.clientProject.maximum_range} Rs.`;
        const clientProjectStatus = data.clientProject.status;
        const clientProjectDuration = data.clientProject.duration;
        const freelancerProjectRange = data.freelancer_range;
        const freelancerProjectDuration = data.duration;
        const freelancerProjectStatus = data.status;
        const freelancerCreatedAt = new Date(data.created_at).toLocaleDateString();


        const row = document.createElement('tr');
        row.classList.add('border-b', 'hover:bg-gray-50');

        row.innerHTML = `
            <td class="px-6 py-4">${clientName}</td>
            <td class="px-6 py-4">${clientProjectTitle}</td>
            <td class="px-6 py-4">${clientProjectRange}</td>
            <td class="px-6 py-4">${clientProjectStatus}</td>
            <td class="px-6 py-4">${clientProjectDuration}</td>
            <td class="px-6 py-4">${freelancerProjectRange}</td>
            <td class="px-6 py-4">${freelancerProjectDuration}</td>
                    <td class="px-6 py-4">${freelancerProjectStatus}</td>
            <td class="px-6 py-4">${freelancerCreatedAt}</td>
        `;

        tableBody.appendChild(row);
    });
}

