async function loadHeader() {
    const container = document.getElementById("header-container");
    const response = await fetch("/assets/components/header.html");
    const html = await response.text();
    container.innerHTML = html;

    // Обработчики кнопок после вставки хедера
    const backBtn = document.getElementById('btn-back');
    const homeBtn = document.getElementById('btn-home');

    if (backBtn) backBtn.addEventListener('click', () => window.history.back());
    if (homeBtn) homeBtn.addEventListener('click', () => window.location.href = "index.html");

    // Инициализация переключателя языка тоже здесь
    initLangSwitcher();
}