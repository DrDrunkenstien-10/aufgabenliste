const API_URL = "http://localhost:4000/api/v1/tasks";

const modal = document.getElementById("task-modal");
const closeModalBtn = document.getElementById("close-modal");
const openAddBtn = document.getElementById("open-add-btn");
const form = document.getElementById("task-form");
const modalTitle = document.getElementById("modal-title");

// ===== Fetch & Render =====
async function fetchTasks() {
    try {
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error("Failed to fetch tasks");
        const tasks = await response.json();
        renderTasks(tasks);
    } catch (error) {
        console.error("Error fetching tasks:", error);
        document.getElementById("task-list").innerHTML =
            `<p class="error">⚠️ Could not load tasks</p>`;
    }
}

function renderTasks(tasks) {
    const taskList = document.getElementById("task-list");
    taskList.innerHTML = "";

    if (tasks.length === 0) {
        taskList.innerHTML = "<p>No tasks available.</p>";
        return;
    }

    tasks.forEach(task => {
        const card = document.createElement("div");
        card.classList.add("task-card");

        const createdDate = new Date(task.createdAt).toLocaleDateString("en-GB", {
            year: "numeric", month: "short", day: "numeric"
        });

        card.innerHTML = `
      <div class="task-header">
        <div class="task-name">${task.name}</div>
        <div class="priority ${task.priority}">${task.priority}</div>
      </div>
      <div class="task-desc">${task.description || "—"}</div>
      <div class="task-date">📅 Created: ${createdDate}</div>
      <div class="task-actions">
        <button onclick="openEditTask('${task.id}', '${task.name}', '${task.description || ""}', '${task.priority}')">Update</button>
        <button onclick="deleteTask('${task.id}')">Delete</button>
      </div>
    `;

        taskList.appendChild(card);
    });
}

// ===== Add or Update =====
async function saveTask(event) {
    event.preventDefault();

    const id = document.getElementById("task-id").value;
    const name = document.getElementById("task-name").value.trim();
    const description = document.getElementById("task-desc").value.trim();
    const priority = document.getElementById("task-priority").value;

    if (!name || name.length < 1 || name.length > 100) {
        alert("Task name must be between 1 and 100 characters.");
        return;
    }
    if (description.length > 500) {
        alert("Description must not exceed 500 characters.");
        return;
    }
    if (!priority) {
        alert("Priority is required.");
        return;
    }

    const taskData = { name, description, priority };

    try {
        let response;
        if (id) {
            response = await fetch(`${API_URL}/${id}`, {
                method: "PATCH",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(taskData)
            });
        } else {
            response = await fetch(API_URL, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(taskData)
            });
        }

        if (!response.ok) throw new Error("Failed to save task");

        closeModal();
        fetchTasks();
    } catch (error) {
        console.error("Error saving task:", error);
        alert("⚠️ Failed to save task: " + error.message);
    }
}

// ===== Edit Task =====
function openEditTask(id, name, description, priority) {
    modalTitle.textContent = "Update Task";
    form.reset();
    document.getElementById("task-id").value = id;
    document.getElementById("task-name").value = name;
    document.getElementById("task-desc").value = description;
    document.getElementById("task-priority").value = priority;
    openModal();
}

// ===== Delete Task =====
async function deleteTask(id) {
    if (!confirm("Are you sure you want to delete this task?")) return;

    try {
        const response = await fetch(`${API_URL}/${id}`, { method: "DELETE" });
        if (!response.ok) throw new Error("Failed to delete task");
        fetchTasks();
    } catch (error) {
        console.error("Error deleting task:", error);
        alert("⚠️ Failed to delete task: " + error.message);
    }
}

// ===== Modal Controls =====
function openModal() {
    modal.style.display = "block";
}
function closeModal() {
    modal.style.display = "none";
    form.reset();
    document.getElementById("task-id").value = "";
    modalTitle.textContent = "Add Task";
}

openAddBtn.addEventListener("click", () => {
    modalTitle.textContent = "Add Task";
    form.reset();
    openModal();
});
closeModalBtn.addEventListener("click", closeModal);
window.addEventListener("click", e => {
    if (e.target === modal) closeModal();
});

// ===== Init =====
document.addEventListener("DOMContentLoaded", () => {
    fetchTasks();
    form.addEventListener("submit", saveTask);
});
