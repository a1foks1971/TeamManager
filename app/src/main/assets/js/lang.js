document.addEventListener('DOMContentLoaded', () => {
  const langSwitcher = document.getElementById('lang-switcher');
  let lang = 'uk'; // язык по умолчанию

  function setLanguage(lang) {
//    document.querySelector('[data-page="home"] #text-back').textContent    = strings.home.back[lang];
//    document.querySelector('[data-page="home"] #text-home').textContent    = strings.home.home[lang];
    document.querySelector('[data-page="home"] #page-title').textContent   = strings.home.teamman[lang];
    document.querySelector('[data-page="home"] #settings-btn').textContent = strings.home.settings[lang];
    document.querySelector('[data-page="home"] #next-btn').textContent     = strings.home.next[lang];
  }

  setLanguage(lang);

  langSwitcher.addEventListener('change', (e) => {
    lang = e.target.value;
    setLanguage(lang);
  });
});
