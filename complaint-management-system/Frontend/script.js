const API_BASE = 'http://localhost:8080/api/complaints';
let currentComplaintId = null;

console.log("Frontend loaded successfully!");

// Test connection function
async function testConnection() {
    try {
        console.log("Testing connection to backend...");
        const response = await fetch(API_BASE);
        console.log("Response status:", response.status);
        if (response.ok) {
            const data = await response.json();
            console.log("Backend connected successfully! Data:", data);
        } else {
            console.log("Backend responded with error:", response.status);
        }
    } catch (error) {
        console.error("Connection failed - Backend might not be running:", error);
        alert("Backend is not running! Please start your Spring Boot application on port 8080.");
    }
}

// Call the test when page loads
setTimeout(testConnection, 1000);

// View Complaint Details - NEW FUNCTION ADDED
async function viewComplaintDetail(complaintId) {
    try {
        console.log("Loading complaint details for ID:", complaintId);
        const response = await fetch(`${API_BASE}/${complaintId}`);
        
        if (!response.ok) {
            throw new Error('Complaint not found');
        }
        
        const complaint = await response.json();
        console.log("Complaint details:", complaint);
        
        // Display complaint details
        document.getElementById('detail-content').innerHTML = `
            <div class="complaint-detail">
                <h3>${complaint.title}</h3>
                <p><strong>Description:</strong> ${complaint.description}</p>
                <p><strong>Category:</strong> ${complaint.category}</p>
                <p><strong>City:</strong> ${complaint.city}</p>
                <p><strong>Reporter:</strong> ${complaint.reporterName}</p>
                <p><strong>Status:</strong> <span class="status-chip status-${complaint.status}">${complaint.status}</span></p>
                <p><strong>Created:</strong> ${new Date(complaint.createdAt).toLocaleString()}</p>
            </div>
        `;
        
        // Show the detail page
        showPage('complaint-detail');
        
    } catch (error) {
        console.error('Error loading complaint details:', error);
        alert('Error loading complaint details: ' + error.message);
    }
}

// Page Navigation
function showPage(pageId) {
    document.querySelectorAll('.page').forEach(page => page.classList.remove('active'));
    document.getElementById(pageId).classList.add('active');

    if (pageId === 'my-complaints') loadMyComplaints();
    if (pageId === 'admin-queue') loadAdminComplaints();
}

// Load My Complaints
async function loadMyComplaints() {
    try {
        const response = await fetch(API_BASE);
        const complaints = await response.json();
        renderComplaints(complaints, 'complaints-list');
    } catch (error) {
        console.error('Error loading complaints:', error);
        alert('Error loading complaints. Check console for details.');
    }
}

// Load Admin Complaints with Filter
async function loadAdminComplaints() {
    try {
        const statusFilter = document.getElementById('status-filter').value;
        let url = API_BASE;
        if (statusFilter) url += `?status=${statusFilter}`;

        const response = await fetch(url);
        const complaints = await response.json();
        renderAdminComplaints(complaints);
    } catch (error) {
        console.error('Error loading admin complaints:', error);
    }
}

// Render Complaints List
function renderComplaints(complaints, containerId) {
    const container = document.getElementById(containerId);
    if (complaints.length === 0) {
        container.innerHTML = '<p>No complaints found.</p>';
        return;
    }

    container.innerHTML = complaints.map(complaint => `
        <div class="complaint-item" onclick="viewComplaintDetail(${complaint.id})">
            <h3>${complaint.title}</h3>
            <p>${complaint.description}</p>
            <div>
                <small>Category: ${complaint.category} | City: ${complaint.city}</small>
                <span class="status-chip status-${complaint.status}">${complaint.status}</span>
            </div>
            <small>Created: ${new Date(complaint.createdAt).toLocaleDateString()}</small>
        </div>
    `).join('');
}

// Render Admin Complaints with Actions
function renderAdminComplaints(complaints) {
    const container = document.getElementById('admin-complaints-list');
    if (complaints.length === 0) {
        container.innerHTML = '<p>No complaints found.</p>';
        return;
    }

    container.innerHTML = complaints.map(complaint => `
        <div class="complaint-item">
            <h3>${complaint.title}</h3>
            <p>${complaint.description}</p>
            <div>
                <small>Reporter: ${complaint.reporterName} | Category: ${complaint.category}</small>
                <span class="status-chip status-${complaint.status}">${complaint.status}</span>
            </div>
            
            <div style="margin-top: 1rem;">
                <select id="staff-select-${complaint.id}">
                    <option value="">Select Staff</option>
                    <option value="Staff 1">Staff 1</option>
                    <option value="Staff 2">Staff 2</option>
                    <option value="Staff 3">Staff 3</option>
                </select>
                <button onclick="assignComplaint(${complaint.id})" style="margin-left: 0.5rem;">Assign</button>
                
                <select id="status-select-${complaint.id}" style="margin-left: 1rem;">
                    <option value="">Change Status</option>
                    <option value="IN_PROGRESS">In Progress</option>
                    <option value="RESOLVED">Resolved</option>
                    <option value="CLOSED">Closed</option>
                </select>
                <input type="text" id="note-${complaint.id}" placeholder="Update note" style="margin-left: 0.5rem;">
                <button onclick="updateStatus(${complaint.id})" style="margin-left: 0.5rem;">Update</button>
            </div>
        </div>
    `).join('');
}

// Submit New Complaint
document.getElementById('complaint-form').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const complaint = {
        reporterName: document.getElementById('reporterName').value,
        category: document.getElementById('category').value,
        city: document.getElementById('city').value,
        title: document.getElementById('title').value,
        description: document.getElementById('description').value
    };

    try {
        const response = await fetch(API_BASE, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(complaint)
        });

        if (response.ok) {
            alert('Complaint submitted successfully!');
            this.reset();
            showPage('my-complaints');
        } else {
            alert('Error submitting complaint');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Error submitting complaint');
    }
});

// Assign Complaint to Staff
async function assignComplaint(complaintId) {
    const staffName = document.getElementById(`staff-select-${complaintId}`).value;
    if (!staffName) {
        alert('Please select a staff member');
        return;
    }

    try {
        const response = await fetch(`${API_BASE}/${complaintId}/assign`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ staffName })
        });

        if (response.ok) {
            alert('Complaint assigned successfully!');
            loadAdminComplaints();
        }
    } catch (error) {
        console.error('Error assigning complaint:', error);
    }
}

// Update Complaint Status
async function updateStatus(complaintId) {
    const newStatus = document.getElementById(`status-select-${complaintId}`).value;
    const note = document.getElementById(`note-${complaintId}`).value;

    if (!newStatus || !note) {
        alert('Please select status and add a note');
        return;
    }

    try {
        const response = await fetch(`${API_BASE}/${complaintId}/update-status`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                user: 'Admin',
                note: note,
                newStatus: newStatus
            })
        });

        if (response.ok) {
            alert('Status updated successfully!');
            loadAdminComplaints();
        }
    } catch (error) {
        console.error('Error updating status:', error);
    }
}

// Initialize the page
showPage('new-complaint');