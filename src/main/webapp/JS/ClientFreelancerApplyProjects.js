/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener('DOMContentLoaded', function () {
    fetchApplyByFreelaancerProjectsDetails();
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
        console.log(freelancerProjects);

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
//        console.log("the freelancer_id: " + data.freelancer.freelancer_id);
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
            <input type="hidden" id="clientFreelancerId" value="${data.freelancer.freelancer_id}" />
            <input type="hidden" id="clientFreelancerName" value="${freelancerName}" />
            <input type="hidden" id="clientFreelancerEmail" value="${data.freelancer.user.email}" />
            <input type="hidden" id="clientFreelancerContact" value="${data.freelancer.contact}" />
            <input type="hidden" id="clientFreelancerProfilePicture" value="${data.freelancer.profile_picture}" />
            <input type="hidden" id="clientFreelancergGithubLink" value="${data.freelancer.github_link}" />
            <input type="hidden" id="clientFreelancerLinkedIn" value="${data.freelancer.linkedin_link}" />
            <input type="hidden" id="clientFreelancerPortfolioLink" value="${data.freelancer.portfolio_link}" />

        `;
        tableBody.appendChild(row);
    });
}


function displayFreelancerEducationDetails(freelancerEducation) {
    const clientFreelancerId = document.getElementById("clientFreelancerId").value;
    const clientFreelancerName = document.getElementById("clientFreelancerName").value;
    const clientFreelancerEmail = document.getElementById("clientFreelancerEmail").value;
    const clientFreelancerContact = document.getElementById("clientFreelancerContact").value;
    const clientFreelancerProfilePicture = document.getElementById("clientFreelancerProfilePicture").value;
    const clientFreelancergGithubLink = document.getElementById("clientFreelancergGithubLink").value;
    const clientFreelancerLinkedIn = document.getElementById("clientFreelancerLinkedIn").value;
    const clientFreelancerPortfolioLink = document.getElementById("clientFreelancerPortfolioLink").value;

    const modalBody = document.getElementById('viewFreelancerDetailsModal');

    document.getElementById("clientsFreelancerIdForShoeModal").value = clientFreelancerId;
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
//            console.log(freelancerEducation);
        } else {
            console.error("No freelancer Education data found.");
        }
    } catch (error) {
        console.error("Error fetching freelancer Education details:", error);
    }
}



function displayFreelancerExperienceDetails(freelancerExperience) {

    const modalBody = document.getElementById('viewFreelancerDetailsModal');

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
    const freelancerId = event.target.closest('tr').querySelector('#clientFreelancerId').value;
    fetchApplyByFreelaancerEducationDetails(freelancerId);
    fetchApplyByFreelaancerExperienceDetails(freelancerId);
    openModal("viewFreelancerDetailsModal");
}