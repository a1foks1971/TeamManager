document.addEventListener('DOMContentLoaded', () => {
  const langSwitcher = document.getElementById('lang-switcher');
  let lang = 'uk';

  const page = document.body.dataset.page;

  function setLanguage(lang) {
    switch (page) {
      case 'home':
        document.querySelector('[data-page="home"] #page-title').textContent   = strings.home.teamman[lang];
        document.querySelector('[data-page="home"] #btn-settings').textContent = strings.home.settings[lang];
        document.querySelector('[data-page="home"] #btn-next').textContent     = strings.home.next[lang];
        break;
      case 'settings':
        document.getElementById('page-title').textContent = strings.settings.settings[lang];
        document.getElementById('db-btn').textContent      = strings.settings.database[lang];
        document.getElementById('users-btn').textContent   = strings.settings.users[lang];
        break;
    }
  }

  setLanguage(lang);

  langSwitcher.addEventListener('change', (e) => {
    lang = e.target.value;
    setLanguage(lang);
  });
});
