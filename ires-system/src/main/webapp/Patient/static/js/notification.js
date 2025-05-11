document.addEventListener("DOMContentLoaded", () => {
    const notifications = document.querySelectorAll(".notification");

    notifications.forEach((notif, index) => {
        setTimeout(() => {
            notif.style.animation = `fadeIn 0.5s ease-in-out ${index * 0.3}s forwards`;
        }, index * 200);
    });
});
function deleteNotification(button) {
    button.parentElement.style.display = 'none';
}
