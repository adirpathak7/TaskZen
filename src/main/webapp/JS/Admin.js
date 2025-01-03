/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', function () {
    fetchClientPendingStatusDetails();
    fetchClientDetails();
    fetchFreelancerDetails();
    fetchTotalUsersDetails();
    animateVisitorCounter(50);
    fetchClintsDetailsForAdmin();
});

async function fetchClientPendingStatusDetails() {
    const apiUrl = "http://localhost:8000/api/client/getClientStatusPending";
    try {
        const response = await fetch(apiUrl, {
            method: "GET"
        });
        if (!response.ok) {
            throw new Error("Failed to fetch pending client status details");
        }

        const result = await response.json();
        const clientPendingStatus = result;

        if (clientPendingStatus && Array.isArray(clientPendingStatus)) {
            displayPendingStatusClientDetails(clientPendingStatus);
        } else {
            console.error("No pending client data found.");
        }
    } catch (error) {
        console.error("Error fetching pending client details:", error);
    }
}

function displayPendingStatusClientDetails(clients) {
    const tableBody = document.getElementById('client-table-body');
    tableBody.innerHTML = '';

    clients.forEach(client => {
        const row = document.createElement('tr');
        const nameCell = document.createElement('td');
        nameCell.classList.add('px-6', 'py-3', 'text-left');
        nameCell.textContent = client.client_name;
        const contactCell = document.createElement('td');
        contactCell.classList.add('px-6', 'py-3', 'text-left');
        contactCell.textContent = client.contact;
        const locationCell = document.createElement('td');
        locationCell.classList.add('px-6', 'py-3', 'text-left');
        locationCell.textContent = client.country;
        const industryCell = document.createElement('td');
        industryCell.classList.add('px-6', 'py-3', 'text-left');
        industryCell.textContent = client.industry;
        const establishCell = document.createElement('td');
        establishCell.classList.add('px-6', 'py-3', 'text-left');
        establishCell.textContent = client.establish;
        const statusCell = document.createElement('td');
        statusCell.classList.add('px-6', 'py-3', 'text-left');
        statusCell.textContent = client.status;

        const actionCell = document.createElement('td');
        const button = document.createElement('button');
        button.classList.add('px-4', 'py-2', 'text-white', 'bg-blue-600', 'rounded');
        button.textContent = 'Approve';
        button.type = 'button';

        const actionCellReject = document.createElement('td');
        const btnCellReject = document.createElement('button');
        btnCellReject.classList.add('px-4', 'py-2', 'text-white', 'bg-red-600', 'rounded');
        btnCellReject.textContent = 'Reject';
        btnCellReject.type = 'button';

// Assume row is the row where the reject button is created
//        document.createElement('tr');  // Example, this should be the row where your button is placed
        row.appendChild(actionCellReject);  // Add your button to the row

        btnCellReject.onclick = function (event) {
            // Confirm the action (if you want to have a confirmation)
            if (confirm("Are you sure you want to reject this?")) {
                // Temporarily hide the entire row
                row.style.display = 'none';
            }
        };

        button.onclick = function (event) {
            changeClientStatus(event, client.client_id);
        };
        actionCell.appendChild(button);
        actionCellReject.appendChild(btnCellReject);
        row.appendChild(nameCell);
        row.appendChild(contactCell);
        row.appendChild(locationCell);
        row.appendChild(industryCell);
        row.appendChild(establishCell);
        row.appendChild(statusCell);
        row.appendChild(actionCell);
        row.appendChild(actionCellReject);
        tableBody.appendChild(row);
    });
}

function changeClientStatus(event, clientId) {
    event.preventDefault();

    const apiUrl = `http://localhost:8000/api/client/approveClientStatus/${clientId}`;

    fetch(apiUrl, {
        method: "PUT"
    }).then(response => response.json()).then(result => {
        if (result.data === "1") {
            alert("Client approved successfully!");
        } else {
            alert("Failed to approved client. Please try again.");
//            console.error(result);
        }
    }).catch(error => {
        console.error("Error occurred while approved client: ", error);
        alert("An error occurred. Please try again.");
    });

}

async function fetchClientDetails() {
    const apiUrl = "http://localhost:8000/api/client/getAllClientsDetails";
    try {
        const response = await fetch(apiUrl, {
            method: "GET"
        });
        if (!response.ok) {
            throw new Error("Failed to fetch client details");
        }

        const result = await response.json();
        const clientDetails = result;
        const totalCountClients = result.length;

        if (clientDetails && Array.isArray(clientDetails)) {
            displayClientDetails(clientDetails);
            animateCounter(totalCountClients, "totalClientCounts");
        } else {
            console.error("No client data found.");
        }
    } catch (error) {
        console.error("Error fetching client details:", error);
    }
}

function displayClientDetails(clientDetails) {
    const clientsTable = document.getElementById("clientsTable");
    clientsTable.innerHTML = "";

    clientDetails.forEach(client => {
        const row = document.createElement("tr");
        row.classList.add("border-b");

        row.innerHTML = `
            <td class="py-3">
                <div class="flex items-center">
                    <div alt="User avatar" class="w-10 h-10 rounded-full mr-3">
                        <i class="ri-account-pin-circle-line text-3xl"></i>
                    </div>
                    <div>
                        <h3 class="text-sm font-semibold text-gray-900">${client.client_name}</h3>
                        <p class="text-xs text-gray-500">${client.user.email}</p>
                    </div>
                </div>
            </td>
            <td class="py-3 text-right">
                <span class="text-gray-500 text-md font-semibold justify-center rounded">${client.contact}</span>
            </td>
        `;

        clientsTable.appendChild(row);
    });
}


async function fetchFreelancerDetails() {
    const apiUrl = "http://localhost:8000/api/freelancer/getAllFreelancersDetails";
    try {
        const response = await fetch(apiUrl, {
            method: "GET"
        });
        if (!response.ok) {
            throw new Error("Failed to fetch Freelancer details");
        }

        const result = await response.json();
        const FreelancerDetails = result;
        const totalCountFreelancer = result.length;

        if (FreelancerDetails && Array.isArray(FreelancerDetails)) {
            displayFreelancerDetails(FreelancerDetails);
            animateCounter(totalCountFreelancer, "totalFreelancerCounts");
        } else {
            console.error("No Freelancer data found.");
        }
    } catch (error) {
        console.error("Error fetching Freelancer details:", error);
    }
}

function displayFreelancerDetails(freelancerDetails) {
    const freelancerTable = document.getElementById("freelancerTable");
    freelancerTable.innerHTML = "";

    freelancerDetails.forEach(freelancer => {
        const row = document.createElement("tr");
        row.classList.add("border-b");

        row.innerHTML = `
            <td class="py-3">
                <div class="flex items-center">
                    <div alt="User avatar" class="w-10 h-10 rounded-full mr-3">
                        <i class="ri-account-pin-circle-line text-3xl"></i>
                    </div>
                    <div>
                        <h3 class="text-sm font-semibold text-gray-900">${freelancer.user.first_name} ${freelancer.user.last_name}</h3>
                        <p class="text-xs text-gray-500">${freelancer.user.email}</p>
                    </div>
                </div>
            </td>
            <td class="py-3 text-right">
                <span class="text-gray-500 text-md font-semibold justify-center rounded">${freelancer.contact}</span>
            </td>
        `;

        freelancerTable.appendChild(row);
    });
}



async function fetchTotalUsersDetails() {
    const apiUrl = "http://localhost:8000/api/count/totalUsers";
    try {
        const response = await fetch(apiUrl, {
            method: "GET"
        });
        if (!response.ok) {
            throw new Error("Failed to fetch Users details");
        }

        const result = await response.json();
//        console.log(result);

        if (typeof result === "number") {
            animateCounter(result, "totalUserCounts");
        } else {
            console.error("No Users data found or incorrect format.");
        }
    } catch (error) {
        console.error("Error fetching Users details:", error);
    }
}

function animateVisitorCounter(target) {
    let currentCount = 0;
    const counterElementForVisitors = document.getElementById("totalVisitors");

    const interval = setInterval(() => {
        currentCount += 1;
        counterElementForVisitors.innerHTML = currentCount;

        if (currentCount === target) {
            clearInterval(interval);
        }
    }, 50);
}

function animateCounter(target, counterElementId) {
    let currentCount = 0;
    const counterElement = document.getElementById(counterElementId);

    const interval = setInterval(() => {
        currentCount += 1;
        counterElement.innerHTML = currentCount;

        if (currentCount === target) {
            clearInterval(interval);
        }
    }, 250);
}


// Clients all details

async function fetchClintsDetailsForAdmin() {
    const apiUrl = "http://localhost:8000/api/client/getAllClientsProjects";

    try {
        const response = await fetch(apiUrl, {
            method: "GET"
        });

        if (!response.ok) {
            throw new Error("Failed to fetch client's details!");
        }

        const result = await response.json();
        const clientsDetails = result.data;
        console.log(result);

        if (result && Array.isArray(result)) {
            displayClintsDetailsForAdmin(result);
        } else {
            console.error("No client's data found.");
        }
    } catch (error) {
        console.error("Error fetching client's details:", error);
    }
}

function displayClintsDetailsForAdmin(result) {
    const tableBody = document.getElementById('displayAllClientsDetailsToAdmin-table-body');
    tableBody.innerHTML = '';

    result.forEach(data => {
        const userName = `${data.client_id.user.first_name} ${data.client_id.user.last_name}`;
        const clientProfilePicture = data.client_id.profile_picture;
        const clientName = data.client_id.client_name;
        const clientEmail = data.client_id.user.email;
        const clientContact = data.client_id.contact;
        const clientProjectTitle = data.client_project_name;
        const clientProjectRange = `${data.minimum_range} - ${data.maximum_range} Rs.`;
        const clientProjectDuration = data.duration;
        const clientProjectStatus = data.status;
        const clientCreatedAt = new Date(data.created_at).toLocaleDateString();


        const row = document.createElement('tr');
        row.classList.add('border-b', 'hover:bg-gray-50');

        row.innerHTML = `
            <div class="flex items-center">
                <div class="w-24 h-24 rounded-full overflow-hidden mr-3">
                    <img src="${clientProfilePicture}" alt="Client Profile Picture" class="w-full h-full object-cover"/>
                </div>
            </div>
            <td class="px-6 py-4">${userName}</td>
            <td class="px-6 py-4">${clientName}</td>
            <td class="px-6 py-4">${clientEmail}</td>
            <td class="px-6 py-4">${clientContact}</td>
            <td class="px-6 py-4">${clientProjectTitle}</td>
            <td class="px-6 py-4">${clientProjectRange}</td>
            <td class="px-6 py-4">${clientProjectDuration}</td>
            <td class="px-6 py-4">${clientProjectStatus}</td>
            <td class="px-6 py-4">${clientCreatedAt}</td>
        `;

        tableBody.appendChild(row);
    });
}
