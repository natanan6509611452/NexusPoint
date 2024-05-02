class Header extends HTMLElement {
  constructor() {
    super();
  }

  connectedCallback() {
    this.innerHTML = `
      <nav class="navbar bg-body-tertiary fixed-top">
              <div class="container-fluid">
                  <button aria-controls="offcanvasNavbar" aria-label="Toggle navigation" class="navbar-toggler"
                      data-bs-target="#offcanvasNavbar" data-bs-toggle="offcanvas" type="button">
                      <span class="navbar-toggler-icon"></span>
                  </button>
                  <div><h2 id="pageTitle"></h2></div>

                  <div></div>
                  <div aria-labelledby="offcanvasNavbarLabel" class="offcanvas offcanvas-start" id="offcanvasNavbar"
                      tabindex="-1">
                      <div class="offcanvas-header">
                          <h5 class="offcanvas-title" id="offcanvasNavbarLabel"></h5>
                          <button aria-label="Close" class="btn-close" data-bs-dismiss="offcanvas" type="button"></button>
                      </div>
                      <div class="offcanvas-body">
                          <ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
                              <li class="nav-item align-self-center">
                                  <h2>สวัสดี, ` + sessionStorage.getItem("fname") + ` </h2>
                              </li>
                              <!-- <li class="nav-item flex-column align-self-start"
                                  style="margin-left: 30px !important; align-items: baseline !important;">
                                  <h3>ชื่อ: <span id="offcanvas-displayname_th"></span></h3>
                                  <h3>รหัสนักศึกษา: <span id="offcanvas-username"></span></h3>
                                  <h3>ภาควิชา: <span id="offcanvas-department"></span></h3>
                                  <h3>อีเมล: <span id="offcanvas-email"></span></h3>
                              </li> -->
                              <li class="nav-item align-self-center">
                                  <button class="btn btn-lg" onclick="window.location.href='profile.html';" type="button">
                                      ข้อมูลส่วนตัว
                                  </button>
                              </li>
                              <li class="nav-item align-self-center" style="margin-top: -10px !important;">
                                  <button class="btn btn-lg" onclick="window.location.href='login.html';"
                                      type="button">ออกจากระบบ</button>
                              </li>
                              <script>
                                  document.getElementById("offcanvas-displayname_th").textContent = sessionStorage.getItem("displayname_th")
                                  document.getElementById("offcanvas-username").textContent = sessionStorage.getItem("username")
                                  document.getElementById("offcanvas-department").textContent = sessionStorage.getItem("department")
                                  document.getElementById("offcanvas-email").textContent = sessionStorage.getItem("email")
                              </script>
                          </ul>
                      </div>
                  </div>
              </div>
          </nav>
    `;
  }
}

customElements.define('header-component', Header);
