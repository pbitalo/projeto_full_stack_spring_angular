import { MenuItem } from 'primeng/api';
import { Component } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  eLogin = true;
  title = 'Indra Poc';
  items: MenuItem[];

  constructor(private router: Router) {}

  ngOnInit() {
    this.carregarMenu();
    this.observarRotas();
  }

  carregarMenu() {
    this.items = [
      {
        label: 'Usuários',
        icon: 'pi pi-users',
        items: [{ label: 'Crud', icon: 'pi pi-search-plus', url: 'usuarios' }],
      },
      {
        label: 'Clientes',
        icon: 'pi pi-id-card',
        items: [{ label: 'Crud', icon: 'pi pi-search-plus', url: 'clientes' }],
      },
      {
        label: 'Produtos',
        icon: 'pi pi-id-card',
        items: [{ label: 'Crud', icon: 'pi pi-search-plus', url: 'produtos' }],
      },
      {
        label: 'Vendas',
        icon: 'pi pi-plus',
        items: [
          { label: 'Crud', icon: 'pi pi-search-plus', url: 'vendas' },
          { label: 'Histórico', icon: 'pi pi-list', url: 'historico' },
        ],
      },
      {
        label: 'Relatórios / Imp. Base',
        icon: 'pi pi-table',
        items: [
          { label: 'Diversos', icon: 'pi pi-search-plus', url: 'relatorios' },
        ],
      },
    ];
  }

  logout() {
    this.eLogin = true;
    this.router.navigateByUrl('/login');
  }

  observarRotas() {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        if (event.url !== '/login' && event.url !== '/') {
          this.eLogin = false;
        }
      }
    });
  }
}
