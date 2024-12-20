/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', function () {
    fetchFreelaancerProjectsDetails();
});

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
            console.error("No freelancer project data found.");
        }
    } catch (error) {
        console.error("Error fetching freelancer project details:", error);
    }
}

function displayFreelancerApplyProjects(freelancerProjects) {
    const tableBody = document.getElementById('table-body');
    tableBody.innerHTML = '';  // Clear existing table content

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
