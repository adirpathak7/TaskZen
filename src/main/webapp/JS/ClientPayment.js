document.addEventListener('DOMContentLoaded', function () {
    fetchTaskPaymentDetails();
    loadRazorpayScript();
});

async function fetchTaskPaymentDetails() {
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
            throw new Error("Failed to fetch freelancer completed project details!");
        }

        const result = await response.json();
        const freelancerProjects = result.data;

        if (freelancerProjects && Array.isArray(freelancerProjects)) {
            displayTaskPaymentDetails(freelancerProjects);
        } else {
            console.error("No freelancer completed project data found.");
        }
    } catch (error) {
        console.error("Error fetching freelancer completed project details:", error);
    }
}

function displayTaskPaymentDetails(freelancerProjects) {
    const tableBody = document.getElementById('completedTaskPaymentDetails-table-body');
    tableBody.innerHTML = '';
    freelancerProjects.forEach((data, index) => {
        const freelancerPost_id = data.post_id;
        const freelancerFreelanceId = data.freelancer.freelancer_id;
        const clientProjectId = data.clientProject.client_project_id;
        const freelancerName = `${data.freelancer.user.first_name} ${data.freelancer.user.last_name}`;
        const clientProjectTitle = data.clientProject.client_project_name;
        const freelancerProjectRange = data.freelancer_range;
        const freelancerDuration = data.duration;
        const freelancerStatus = data.status;
        const clientPostDate = data.clientProject.created_at;

        const row = document.createElement('tr');
        row.classList.add('border-t', 'hover:bg-gray-100');
        row.innerHTML = `
            <td class="px-6 py-4">${freelancerName}</td>
            <td class="px-6 py-4">${clientProjectTitle}</td>
            <td class="px-6 py-4">${freelancerProjectRange}</td>
            <td class="px-6 py-4">${freelancerDuration}</td>
            <td class="px-6 py-4">${freelancerDuration}</td>
            <td class="px-6 py-4">${freelancerStatus}</td>
            <td class="px-6 py-4">${clientPostDate}</td>

            <button class="px-4 py-2 bg-purple-600 text-white rounded-md pay-btn" id="pay-btn-${index}" type="button">
                Pay
            </button>
            <input type="hidden" name="amount" value="${freelancerProjectRange}"/>
            <input type="hidden" name="freelancer_id" value="${freelancerFreelanceId}"/>
            <input type="hidden" name="post_id" value="${freelancerPost_id}"/>
            <input type="hidden" name="client_project_id" value="${clientProjectId}"/>
        `;
        tableBody.appendChild(row);

        document.getElementById(`pay-btn-${index}`).onclick = async function (e) {
            e.preventDefault();
            const amountInRupees = freelancerProjectRange;
            const amountInPaise = amountInRupees * 100;

            if (amountInPaise < 100) {
                alert("The minimum payment amount is 1 INR (100 paise).");
                return;
            }

            try {
                const response = await fetch(`http://localhost:8000/api/client/pay/freelancer/${freelancerFreelanceId}/${clientProjectId}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        amount: amountInPaise
                    })
                });
                const order = await response.json();
                if (order.error) {
                    alert("Unable to create the order. Please try again.");
                    return;
                }

                var options = {
                    "key": "rzp_test_hQvo3t0ixFDexf",
                    "amount": amountInPaise,
                    "currency": "INR",
                    "name": "AADITYA PATHAK",
                    "description": "Order for Test",
                    "order_id": order.razorpayOrderId,
                    "callback_url": "http://localhost:8080/TaskZen/Dashboard/Client/Maindashboard.xhtml",
                    "prefill": {
                        "name": order.name,
                        "email": order.email
                    },
                    "theme": {
                        "color": "#339900"
                    }
                };
                const rzp1 = new Razorpay(options);

                rzp1.on('payment.failed', function (response) {
                    alert("Payment failed. Please try again.");
                });

                rzp1.on('payment.success', function (response) {
                    // Disable the "Pay" button after payment success
                    const payButton = document.getElementById(`pay-btn-${index}`);
                    payButton.disabled = true;
                    payButton.innerHTML = "Paid"; // Change button text to "Paid"
                });

                rzp1.open();
            } catch (error) {
                console.error("Error while processing payment:", error);
                alert("An error occurred. Please try again.");
            }
        };
    });
}

function loadRazorpayScript(callback) {
    const script = document.createElement("script");
    script.src = "https://checkout.razorpay.com/v1/checkout.js";
    script.onerror = function () {
        alert("Razorpay script failed to load.");
    };
    document.head.appendChild(script);
}
