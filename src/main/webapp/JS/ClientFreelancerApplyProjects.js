
/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener('DOMContentLoaded', function () {
    fetchApplyByFreelaancerProjectsDetails();
    fetchAcceptedFreelaancerProjectsDetails();
    fetchCompletedFreelaancerProjectsDetails();
});

async function fetchApplyByFreelaancerProjectsDetails() {
    const apiUrl = "http://localhost:8000/api/freelancer/getClientsFreelancersByProjectsByToken";
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
//        console.log("freelancerProjects", freelancerProjects);

        if (freelancerProjects && Array.isArray(freelancerProjects)) {
            displayFreelancerApplyForClientProjects(freelancerProjects);
        } else {
            console.error("No freelancer project data found.");
        }
    } catch (error) {
        console.error("Error fetching freelancer project details:", error);
    }
}


function displayFreelancerApplyForClientProjects(freelancerProjects) {
    const tableBody = document.getElementById('table-body');
    tableBody.innerHTML = '';
    freelancerProjects.forEach(data => {
        const freelancerName = `${data.freelancer.user.first_name} ${data.freelancer.user.last_name}`;
        const clientProjectTitle = data.clientProject.client_project_name;
        const clientProjectRange = `${data.clientProject.minimum_range} - ${data.clientProject.maximum_range} Rs.`;
        const clientProjectStatus = data.clientProject.status;
        const clientProjectDuration = data.clientProject.duration;
        const freelancerProjectRange = data.freelancer_range;
        const freelancerProjectDuration = data.duration;
        const freelancerProjectStatus = data.status;
        const clientCreatedAt = new Date(data.clientProject.created_at).toLocaleDateString();
//        console.log("the client_project_id: " + data.clientProject.client_project_id);
        const row = document.createElement('tr');
        row.classList.add('border-t', 'hover:bg-gray-100');
        row.innerHTML = `
            <td class="px-6 py-4">${freelancerName}</td>
            <td class="px-6 py-4">${clientProjectTitle}</td>
            <td class="px-6 py-4">${clientProjectRange}</td>
            <td class="px-6 py-4">${clientProjectStatus}</td>
            <td class="px-6 py-4">${clientProjectDuration}</td>
            <td class="px-6 py-4">${freelancerProjectRange}</td>
            <td class="px-6 py-4">${freelancerProjectDuration}</td>
            <td class="px-6 py-4">${freelancerProjectStatus}</td>
            <td class="px-6 py-4">${clientCreatedAt}</td>
            <td class="px-6 py-4">
                <button onclick="viewFreelancerDetails(event)" class = "bg-blue-600 text-white px-4 py-2 rounded" >
                View
                </button>
            </td>
            <td class="px-6 py-4">
                <button onclick="rejectFreelancerDetails(event)" class = "bg-red-600 text-white px-4 py-2 rounded" >
                Reject
                </button>
            </td>
            <input type="hidden" id="clientFreelancerId" class="clientFreelancerId" value="${data.freelancer.freelancer_id}" />
            <input type="hidden" id="clientFreelancerName" class="clientFreelancerName" value="${freelancerName}" />
            <input type="hidden" id="clientFreelancerEmail" class="clientFreelancerEmail" value="${data.freelancer.user.email}" />
            <input type="hidden" id="clientFreelancerContact" class="clientFreelancerContact" value="${data.freelancer.contact}" />
            <input type="hidden" id="clientFreelancerProfilePicture" class="clientFreelancerProfilePicture" value="${data.freelancer.profile_picture}" />
            <input type="hidden" id="clientFreelancergGithubLink" class="clientFreelancergGithubLink" value="${data.freelancer.github_link}" />
            <input type="hidden" id="clientFreelancerLinkedIn" class="clientFreelancerLinkedIn" value="${data.freelancer.linkedin_link}" />
            <input type="hidden" id="clientFreelancerPortfolioLink" class="clientFreelancerPortfolioLink" value="${data.freelancer.portfolio_link}" />
            <input type="hidden" id="freelancerProjectId" class="freelancerProjectId" value="${data.post_id}" />
            <input type="hidden" id="clientProjectId" class="clientProjectId" value="${data.clientProject.client_project_id}" />

        `;
        tableBody.appendChild(row);
    });
}


function displayFreelancerEducationDetails(freelancerEducation) {
    const clientFreelancerId = document.getElementById("clientFreelancerId").value;
    const clientsProjectId = document.getElementById("clientProjectId").value;
    const clientFreelancerName = document.getElementById("clientFreelancerName").value;
    const clientFreelancerEmail = document.getElementById("clientFreelancerEmail").value;
    const clientFreelancerContact = document.getElementById("clientFreelancerContact").value;
    const clientFreelancerProfilePicture = document.getElementById("clientFreelancerProfilePicture").value;
    const clientFreelancergGithubLink = document.getElementById("clientFreelancergGithubLink").value;
    const clientFreelancerLinkedIn = document.getElementById("clientFreelancerLinkedIn").value;
    const clientFreelancerPortfolioLink = document.getElementById("clientFreelancerPortfolioLink").value;

    const modalBody = document.getElementById('viewFreelancerDetailsModal');
//    console.log("freelancer_id", clientFreelancerId);
    document.getElementById("freelancer_id").value = clientFreelancerId;
    document.getElementById("client_project_id").value = clientsProjectId;
    document.getElementById("clientsFreelancerName").innerHTML = clientFreelancerName;
    document.getElementById("clientsFreelancerEmail").innerHTML = clientFreelancerEmail;
    document.getElementById("clientsFreelancerContact").innerHTML = clientFreelancerContact;
    document.getElementById("clientsFreelancerprofile_picture").src = clientFreelancerProfilePicture;
    document.getElementById("clientsFreelancergithub_link").href = clientFreelancergGithubLink;
    document.getElementById("clientsFreelancerlinkedin_link").href = clientFreelancerLinkedIn;
    document.getElementById("clientsFreelancerportfolio_link").href = clientFreelancerPortfolioLink;

    freelancerEducation.forEach(data => {
        document.getElementById("clientsFreelancerUniversity").innerHTML = data.university;
        document.getElementById("clientsFreelancerCoures").innerHTML = data.course;
        document.getElementById("clientsFreelancerstart_date").innerHTML = data.start_date;
        document.getElementById("clientsFreelancerend_date").innerHTML = data.end_date;
        document.getElementById("clientsFreelancerUniversity").innerHTML = data.university;
        document.getElementById("clientsFreelancerUniversity").innerHTML = data.university;

    });

}

async function fetchApplyByFreelaancerEducationDetails(freelancer_id) {
    const apiUrl = `http://localhost:8000/api/freelancer/getFreelancerEducationDetailsById/${freelancer_id}`;
    try {
        const response = await fetch(apiUrl, {
            method: "GET"
        });
        if (!response.ok) {
            throw new Error("Failed to fetch freelancer Education details!");
        }

        const result = await response.json();
        const freelancerEducation = result.data;
        if (freelancerEducation && Array.isArray(freelancerEducation)) {
            displayFreelancerEducationDetails(freelancerEducation);
//            console.log("education:", freelancerEducation);
        } else {
            console.error("No freelancer Education data found.");
        }
    } catch (error) {
        console.error("Error fetching freelancer Education details:", error);
    }
}



function displayFreelancerExperienceDetails(freelancerExperience) {
    freelancerExperience.forEach(data => {
        document.getElementById("clientsFreelancercompany_name").innerHTML = data.company_name;
        document.getElementById("clientsFreelancerdesignation").innerHTML = data.designation;
        document.getElementById("clientsFreelancerstarting_date").innerHTML = data.starting_date;
        document.getElementById("clientsFreelancerending_date").innerHTML = data.ending_date;
    });
}


async function fetchApplyByFreelaancerExperienceDetails(freelancer_id) {
    const apiUrl = `http://localhost:8000/api/freelancer/getFreelancerExperienceDetailsById/${freelancer_id}`;
    try {
        const response = await fetch(apiUrl, {
            method: "GET"
        });
        if (!response.ok) {
            throw new Error("Failed to fetch freelancer Experience details!");
        }

        const result = await response.json();
        const freelancerExperience = result.data;
        if (freelancerExperience && Array.isArray(freelancerExperience)) {
            displayFreelancerExperienceDetails(freelancerExperience);
//            console.log(freelancerExperience);
        } else {
            console.error("No freelancer Experience data found.");
        }
    } catch (error) {
        console.error("Error fetching freelancer Experience details:", error);
    }
}

function viewFreelancerDetails(event) {
    event.preventDefault();

    const row = event.target.closest('tr');

    const freelancerId = row.querySelector('.clientFreelancerId').value;
    const freelancerName = row.querySelector('.clientFreelancerName').value;
    const freelancerEmail = row.querySelector('.clientFreelancerEmail').value;
    const freelancerContact = row.querySelector('.clientFreelancerContact').value;
    const freelancerProfilePicture = row.querySelector('.clientFreelancerProfilePicture').value;
    const freelancerGithubLink = row.querySelector('.clientFreelancergGithubLink').value;
    const freelancerLinkedIn = row.querySelector('.clientFreelancerLinkedIn').value;
    const freelancerPortfolioLink = row.querySelector('.clientFreelancerPortfolioLink').value;
    const clientProjectId = row.querySelector('.clientProjectId').value;

    document.getElementById("freelancer_id").value = freelancerId;
    document.getElementById("client_project_id").value = clientProjectId;
    document.getElementById("clientsFreelancerName").innerHTML = freelancerName;
    document.getElementById("clientsFreelancerEmail").innerHTML = freelancerEmail;
    document.getElementById("clientsFreelancerContact").innerHTML = freelancerContact;
    document.getElementById("clientsFreelancerprofile_picture").src = freelancerProfilePicture;
    document.getElementById("clientsFreelancergithub_link").href = freelancerGithubLink;
    document.getElementById("clientsFreelancerlinkedin_link").href = freelancerLinkedIn;
    document.getElementById("clientsFreelancerportfolio_link").href = freelancerPortfolioLink;

    fetchApplyByFreelaancerEducationDetails(freelancerId);
    fetchApplyByFreelaancerExperienceDetails(freelancerId);

    openModal("viewFreelancerDetailsModal");
}


function rejectFreelancerDetails(event) {
    event.preventDefault();

    const clientConfirmed = confirm("Are you sure you want to reject the freelancer request?");
    if (!clientConfirmed) {
        return;
    }

    const client_project_id = document.getElementById("clientProjectId").value;
    const freelancer_id = document.getElementById("clientFreelancerId").value;

    console.log("Request Body:", JSON.stringify({
        client_project_id: client_project_id,
        freelancer_id: freelancer_id
    }));

//    console.log("client_project_id: ", client_project_id);
//    console.log("freelancer_id: ", freelancer_id);

    alert(client_project_id);
    alert(freelancer_id);
    const token = sessionStorage.getItem("authToken");

    const apiUrl = `http://localhost:8000/api/freelancer/rejectFreelancerProjectStatus?client_project_id=${client_project_id}&freelancer_id=${freelancer_id}`;

    const requestData = {
        client_project_id: client_project_id,
        freelancer_id: freelancer_id
    };

    fetch(apiUrl, {
        method: "PUT",
        headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(requestData)
    })
            .then(response => response.json())
            .then(result => {
                if (result.data === "1") {
                    alert("Client request rejected successfully!");
                    closeModal("viewFreelancerDetailsModal");
                    fetchApplyByFreelaancerProjectsDetails();
                } else {
                    alert("Failed to reject client. Please try again.");
                    console.error(result);
                }
            })
            .catch(error => {
                console.error("Error occurred while rejecting client: ", error);
                alert("An error occurred. Please try again.");
            });
}

function clientFreelancerHireButton(event) {
    event.preventDefault();

    const client_project_id = document.getElementById("client_project_id").value;
    const freelancer_id = document.getElementById("freelancer_id").value;

//    console.log("Request Body:", JSON.stringify({
//        client_project_id: client_project_id,
//        freelancer_id: freelancer_id
//    }));

    alert(client_project_id);
    alert(freelancer_id);
    const token = sessionStorage.getItem("authToken");

    const apiUrl = `http://localhost:8000/api/freelancer/approveFreelancerProjectStatus?client_project_id=${client_project_id}&freelancer_id=${freelancer_id}`;

    const requestData = {
        client_project_id: client_project_id,
        freelancer_id: freelancer_id
    };

    fetch(apiUrl, {
        method: "PUT",
        headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(requestData)
    })
            .then(response => response.json())
            .then(result => {
                if (result.data === "1") {
                    alert("Client request approved successfully!");
                    closeModal("viewFreelancerDetailsModal");
                    fetchApplyByFreelaancerProjectsDetails();
                    const statusUpdateUrl = `http://localhost:8000/api/client/updateStatusInprogress/${client_project_id}`;

                    fetch(statusUpdateUrl, {
                        method: "PUT"
//                        headers: {
//                            "Authorization": `Bearer ${token}`,
//                            "Content-Type": "application/json"
//                        }
                    })
                            .then(response => response.json())
                            .then(statusUpdateResult => {
                                if (statusUpdateResult) {
                                    console.log("Project status updated to In Progress.");
                                } else {
//                                    alert("Failed to update project status.");
                                    console.error(statusUpdateResult);
                                }
                            })
                            .catch(error => {
                                console.error("Error occurred while updating project status: ", error);
//                                alert("An error occurred while updating the project status.");
                            });

                } else {
//                    alert("Failed to approve client. Please try again.");
                    console.error(result);
                }
            })
            .catch(error => {
                console.error("Error occurred while approving client: ", error);
//                alert("An error occurred. Please try again.");
            });
}



async function fetchAcceptedFreelaancerProjectsDetails() {
    const apiUrl = "http://localhost:8000/api/freelancer/getClientsFreelancersByProjectsByToken/accepted";
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
            throw new Error("Failed to fetch freelancer accepted project details!");
        }

        const result = await response.json();
        const freelancerProjects = result.data;
//        console.log("freelancerProjects accepted", freelancerProjects);

        if (freelancerProjects && Array.isArray(freelancerProjects)) {
            displayAcceptedFreelancerApplyForClientProjects(freelancerProjects);
        } else {
            console.error("No freelancer accepted project data found.");
        }
    } catch (error) {
        console.error("Error fetching freelancer accepted project details:", error);
    }
}


function displayAcceptedFreelancerApplyForClientProjects(freelancerProjects) {
    const tableBody = document.getElementById('acceptedFreelancerDetails-table-body');
    tableBody.innerHTML = '';
    freelancerProjects.forEach(data => {
        const freelancerName = `${data.freelancer.user.first_name} ${data.freelancer.user.last_name}`;
        const clientProjectTitle = data.clientProject.client_project_name;
        const clientProjectRange = `${data.clientProject.minimum_range} - ${data.clientProject.maximum_range} Rs.`;
        const clientProjectStatus = data.clientProject.status;
        const clientProjectDuration = data.clientProject.duration;
        const freelancerProjectRange = data.freelancer_range;
        const freelancerProjectDuration = data.duration;
        const freelancerProjectStatus = data.status;
        const clientCreatedAt = new Date(data.clientProject.created_at).toLocaleDateString();

        const row = document.createElement('tr');
        row.classList.add('border-t', 'hover:bg-gray-100');
        row.innerHTML = `
            <td class="px-6 py-4">${freelancerName}</td>
            <td class="px-6 py-4">${clientProjectTitle}</td>
            <td class="px-6 py-4">${freelancerProjectRange}</td>
            <td class="px-6 py-4">${freelancerProjectDuration}</td>
            <td class="px-6 py-4">In Progress</td>
            <td class="px-6 py-4">${clientCreatedAt}</td>
        `;
        tableBody.appendChild(row);
    });
}

//Freelancer Complete Projects

function displayCompletedFreelancerApplyForClientProjects(freelancerProjects) {
    const tableBody = document.getElementById('completedFreelancerDetails-table-body');
    tableBody.innerHTML = '';

    freelancerProjects.forEach(data => {
        const freelancerName = `${data.freelancer.user.first_name} ${data.freelancer.user.last_name}`;
        const clientProjectTitle = data.clientProject.client_project_name;
        const clientProjectRange = `${data.clientProject.minimum_range} - ${data.clientProject.maximum_range} Rs.`;
        const clientProjectStatus = data.clientProject.status;
        const clientProjectDuration = data.clientProject.duration;
        const freelancerProjectRange = data.freelancer_range;
        const freelancerProjectDuration = data.duration;
        const freelancerProjectStatus = data.status;
        const clientCreatedAt = new Date(data.clientProject.created_at).toLocaleDateString();

        const row = document.createElement('tr');
        row.classList.add('border-t', 'hover:bg-gray-100');
        row.innerHTML = `
            <td class="px-6 py-4">${freelancerName}</td>
            <td class="px-6 py-4">${clientProjectTitle}</td>
            <td class="px-6 py-4">${freelancerProjectRange}</td>
            <td class="px-6 py-4">${freelancerProjectDuration}</td>
            <td class="px-6 py-4">${clientProjectStatus}</td>
            <td class="px-6 py-4">${clientCreatedAt}</td>
        `;
        tableBody.appendChild(row);
    });
}


// Freelancer Completed projects
async function fetchCompletedFreelaancerProjectsDetails() {
    const apiUrl = "http://localhost:8000/api/freelancer/getClientsFreelancersByProjectsByToken/completed";
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
            throw new Error("Failed to fetch completed freelancer project details!");
        }

        const result = await response.json();
//        console.log(result);

        const freelancerProjects = result.data;
        if (freelancerProjects && Array.isArray(freelancerProjects)) {
            displayCompletedFreelancerApplyProjects(freelancerProjects);
        } else {
//            console.error("No completed freelancer project data found.");
        }
    } catch (error) {
        console.error("Error fetching completed freelancer project details:", error);
    }
}

function displayCompletedFreelancerApplyProjects(freelancerProjects) {
    const tableBody = document.getElementById('completedFreelancerDetails-table-body');
    tableBody.innerHTML = '';

    freelancerProjects.forEach(data => {
        const clientName = data.client_id.client_name;
        const clientProjectTitle = data.clientProject.client_project_name;
        const clientProjectRange = `${data.clientProject.minimum_range} - ${data.clientProject.maximum_range} Rs.`;
        const clientProjectStatus = data.clientProject.status;
        const clientProjectDuration = data.clientProject.duration;
        const freelancerProjectRange = data.freelancer_range;
        const freelancerName = `${data.freelancer.user.first_name} ${data.freelancer.user.last_name}`;
        const freelancerProjectDuration = data.duration;
        const freelancerProjectStatus = data.status;
        const freelancerCreatedAt = new Date(data.created_at).toLocaleDateString();

        const row = document.createElement('tr');
        row.classList.add('border-b', 'hover:bg-gray-50');

        row.innerHTML = `
            <td class="px-6 py-4">${freelancerName}</td>
            <td class="px-6 py-4">${clientProjectTitle}</td>
            <td class="px-6 py-4">${freelancerProjectDuration}</td>
            <td class="px-6 py-4">${freelancerProjectRange}</td>
            <td class="px-6 py-4">${freelancerProjectStatus}</td>
            <td class="px-6 py-4">${freelancerCreatedAt}</td>
        `;

        tableBody.appendChild(row);
    });
}
