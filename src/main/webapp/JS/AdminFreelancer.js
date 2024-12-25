/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', function () {
    fetchAcceptedAndCompletedFreelaancerProjectsDetails();
});

// Freelancer Accepted & Completed projects

async function fetchAcceptedAndCompletedFreelaancerProjectsDetails() {
    const apiUrl = "http://localhost:8000/api/freelancer/getFreelancerProjectDetails/accepted&completed";

    try {
        const response = await fetch(apiUrl, {
            method: "GET"
        });

        if (!response.ok) {
            throw new Error("Failed to fetch completed freelancer project details!");
        }

        const result = await response.json();
        const freelancerProjects = result.data;
//        console.log(freelancerProjects);

        if (freelancerProjects && Array.isArray(freelancerProjects)) {
            displayAcceptedAndCompletedFreelancerApplyProjects(freelancerProjects);
        } else {
            console.error("No freelancer project data found.");
        }
    } catch (error) {
        console.error("Error fetching freelancer project details:", error);
    }
}

function displayAcceptedAndCompletedFreelancerApplyProjects(freelancerProjects) {
    const tableBody = document.getElementById('displayAcceptedAndCompletedFreelancerApplyProjects-table-body');
    tableBody.innerHTML = '';

    freelancerProjects.forEach(data => {
        const freelancerName = `${data.freelancer.user.first_name} ${data.freelancer.user.last_name}`;
        const freelancerEmail = data.freelancer.user.email;
        const freelancerContact = data.freelancer.contact;
        const freelancerProfilePicture = data.freelancer.profile_picture;
        const clientName = data.client_id.client_name;
        const clientProjectTitle = data.clientProject.client_project_name;
        const clientProjectRange = `${data.clientProject.minimum_range} - ${data.clientProject.maximum_range} Rs.`;
        const freelancerProjectDuration = data.duration;
        const freelancerProjectStatus = data.status;
        const freelancerCreatedAt = new Date(data.created_at).toLocaleDateString();


        const row = document.createElement('tr');
        row.classList.add('border-b', 'hover:bg-gray-50');

        row.innerHTML = `
            <div class="flex items-center">
                <div class="w-24 h-24 rounded-full overflow-hidden mr-3">
                    <img src="${freelancerProfilePicture}" alt="Freelancer Profile Picture" class="w-full h-full object-cover"/>
                </div>
            </div>
            <td class="px-6 py-4">${freelancerName}</td>
            <td class="px-6 py-4">${freelancerEmail}</td>
            <td class="px-6 py-4">${freelancerContact}</td>
            <td class="px-6 py-4">${clientName}</td>
            <td class="px-6 py-4">${clientProjectTitle}</td>
            <td class="px-6 py-4">${clientProjectRange}</td>
            <td class="px-6 py-4">${freelancerProjectDuration}</td>
            <td class="px-6 py-4">${freelancerProjectStatus}</td>
            <td class="px-6 py-4">${freelancerCreatedAt}</td>
        `;

        tableBody.appendChild(row);
    });
}
