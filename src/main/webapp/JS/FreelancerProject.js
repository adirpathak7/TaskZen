/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', function () {
    fetchFreelaancerProjectsDetails();

    fetchAcceptedFreelaancerProjectsDetailsForFreelancer();

    fetchRejectedFreelaancerProjectsDetails();

    fetchPendingFreelaancerProjectsDetails();

    fetchCompletedFreelaancerProjectsDetails();
});

// Freelancer all projects

async function fetchFreelaancerProjectsDetails() {
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
            displayFreelancerApplyProjects(freelancerProjects);
        } else {
//            console.error("No freelancer project data found.");
        }
    } catch (error) {
        console.error("Error fetching freelancer project details:", error);
    }
}

function displayFreelancerApplyProjects(freelancerProjects) {
    const tableBody = document.getElementById('displayFreelancerApplyProjects-table-body');
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


// Freelancer Pending projects

async function fetchPendingFreelaancerProjectsDetails() {
    const apiUrl = "http://localhost:8000/api/freelancer/getFreelancerAppliedPostByToken/pending";
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
            throw new Error("Failed to fetch pending freelancer project details!");
        }

        const result = await response.json();
        const freelancerProjects = result.data;
//        console.log("pending"+freelancerProjects);

        if (freelancerProjects && Array.isArray(freelancerProjects)) {
            displayPendingFreelancerApplyProjects(freelancerProjects);
        } else {
//            console.error("No pending freelancer project data found.");
        }
    } catch (error) {
        console.error("Error fetching pending freelancer project details:", error);
    }
}

function displayPendingFreelancerApplyProjects(freelancerProjects) {
    const tableBody = document.getElementById('displayPendingFreelancerApplyProjects-table-body');
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
            <td class="px-6 py-4">${freelancerCreatedAt}</td>
        `;

        tableBody.appendChild(row);
    });
}



// Freelancer Accepted projects

async function fetchAcceptedFreelaancerProjectsDetailsForFreelancer() {
    const apiUrl = "http://localhost:8000/api/freelancer/getFreelancerAppliedPostByToken/accepted";
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
            throw new Error("Failed to fetch accepted freelancer project details!");
        }

        const result = await response.json();
        const freelancerProjects = result.data;
//        console.log("ab,asmnb " + freelancerProjects);
//        console.log("result " + result);


        if (freelancerProjects && Array.isArray(freelancerProjects)) {
            displayAcceptedFreelancerApplyProjectsForFreelancer(freelancerProjects);
        } else {
//            console.error("No accepted freelancer project data found.");
        }
    } catch (error) {
        console.error("Error fetching accepted freelancer project details:", error);
    }
}

function displayAcceptedFreelancerApplyProjectsForFreelancer(freelancerProjects) {
    const tableBody = document.getElementById('displayAcceptedFreelancerApplyProjects-table-body');
    tableBody.innerHTML = '';

    freelancerProjects.forEach(data => {
        const clientName = data.client_id.client_name;
        const clientProjectTitle = data.clientProject.client_project_name;
        const clientProjectIdForStatusCompletedFreelancer = data.clientProject.client_project_id;
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
            <td class="px-6 py-4">${freelancerCreatedAt}</td>
            <td class="px-6 py-4">
            <button type="submit" onclick="updateStatusCompleted(event)" class="px-2 py-2 bg-purple-600 text-white rounded-md">
                Mark as Done
            </button>
            </td>
            <td class="px-6 py-4">
                <input type="hidden" value="${clientProjectIdForStatusCompletedFreelancer}" id="clientProjectIdForStatusCompletedFreelancer" />
            </td>
        `;

        tableBody.appendChild(row);
    });
}


// Freelancer Rejected projects

async function fetchRejectedFreelaancerProjectsDetails() {
    const apiUrl = "http://localhost:8000/api/freelancer/getFreelancerAppliedPostByToken/rejected";
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
            throw new Error("Failed to fetch rejected freelancer project details!");
        }

        const result = await response.json();
        const freelancerProjects = result.data;
//        console.log(freelancerProjects);

        if (freelancerProjects && Array.isArray(freelancerProjects)) {
            displayRejectedFreelancerApplyProjects(freelancerProjects);
        } else {
//            console.error("No rejected freelancer project data found.");
        }
    } catch (error) {
        console.error("Error fetching rejected freelancer project details:", error);
    }
}

function displayRejectedFreelancerApplyProjects(freelancerProjects) {
    const tableBody = document.getElementById('displayRejectedFreelancerApplyProjects-table-body');
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
            <td class="px-6 py-4">${freelancerCreatedAt}</td>
        `;

        tableBody.appendChild(row);
    });
}



function updateStatusCompleted(event) {

    event.preventDefault();

    const client_project_id = document.getElementById("clientProjectIdForStatusCompletedFreelancer").value;

//    alert(client_project_id);

    const token = sessionStorage.getItem("authToken");

    const apiUrl = `http://localhost:8000/api/freelancer/completeFreelancerProjectStatus?client_project_id=${client_project_id}`;

    const requestData = {
        client_project_id: client_project_id
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
                    alert("Client project completed successfully!");
                    fetchCompletedFreelaancerProjectsDetails();
                } else {
                    alert("Failed to complete project. Please try again.");
                    console.error(result);
                }
            })
            .catch(error => {
                console.error("Error occurred while completing project: ", error);
                alert("An error occurred. Please try again.");
            });
}


//Freelancer Completed project


async function fetchCompletedFreelaancerProjectsDetails() {
    const apiUrl = "http://localhost:8000/api/freelancer/getFreelancerAppliedPostByToken/completed";
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
            throw new Error("Failed to fetch complete freelancer project details!");
        }

        const result = await response.json();
        const freelancerProjects = result.data;
//        console.log("complete: "+freelancerProjects);

        if (freelancerProjects && Array.isArray(freelancerProjects)) {
            displayCompletedFreelancerApplyProjects(freelancerProjects);
        } else {
//            console.error("No pending freelancer project data found.");
        }
    } catch (error) {
        console.error("Error fetching complete freelancer project details:", error);
    }
}

function displayCompletedFreelancerApplyProjects(freelancerProjects) {
    const tableBody = document.getElementById('displayCompletedFreelancerApplyProjects-table-body');
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
            <td class="px-6 py-4">${freelancerCreatedAt}</td>
        `;

        tableBody.appendChild(row);
    });
}