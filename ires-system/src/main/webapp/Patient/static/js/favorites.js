// 刪除資料功能
document.querySelectorAll('.delete-button').forEach(button => {
    button.addEventListener('click', function() {
        const row = this.parentElement; // 獲取診所列
        row.parentElement.removeChild(row); // 刪除該列
    });
});