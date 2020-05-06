import { Component, Injector } from '@angular/core';
import { CrudComponent } from 'src/app/compartilhados/componente-padrao/crud-component';
import { RelatoriosService } from '../servicos/Relatorios.service';
import { FormBuilder } from '@angular/forms';
import { MunicipioService } from 'src/app/compartilhados/servicos/entidades/municipios.service';
import { RegiaoService } from 'src/app/compartilhados/servicos/entidades/regiao.service';
import { ImportarCsvService } from 'src/app/compartilhados/servicos/entidades/importar-csv.service';
import { BandeiraService } from 'src/app/compartilhados/servicos/entidades/bandeira.service';

@Component({
  selector: 'app-relatorios-form',
  templateUrl: './relatorios-form.component.html',
  styleUrls: ['./relatorios-form.component.css'],
})
export class RelatoriosFormComponent extends CrudComponent {
  items: [][];
  listaMunicipios: String[];
  listaRegioes: String[];
  listaBandeiras: String[];
  carregando = false;
  filtroAtivo = '';

  constructor(
    protected servicos: RelatoriosService,
    protected injector: Injector,
    private formBuilder: FormBuilder,
    private servicoMunicipio: MunicipioService,
    private servicoBandeira: BandeiraService,
    private regiaoService: RegiaoService,
    private importarCsvService: ImportarCsvService
  ) {
    super(servicos, injector);
  }

  ngOnInit() {
    super.ngOnInit();
    this.criarOuvintes();
  }

  private criarOuvintes() {
    this.formulario.get('nomeMunicipio').valueChanges.subscribe((valor) => {
      if (valor) {
        this.pegarMediaPrecoComb(valor);
        this.pegarMediaPrecoCompra(valor);
      }
    });
    this.formulario.get('nomeBandeira').valueChanges.subscribe((valor) => {
      if (valor) {
        this.pegarMediasPrecoPorBandeira(valor);
      }
    });
    this.formulario.get('nomeRegiao').valueChanges.subscribe((valor) => {
      if (valor) {
        this.config.currentPage = 1;
        this.filtroAtivo = 'regiao';
        this.pegarDadosPorRegiao(valor);
      }
    });
  }

  mudarPagina(event) {
    this.config.currentPage = event;

    switch (this.filtroAtivo) {
      case 'distribuidora':
        console.log('Case distribuidora');
        this.pegarDadosPorDistribuidora();
        break;
      case 'data':
        console.log('Case data');
        this.pegarDadosPorData();
        break;
      case 'regiao':
        console.log('Case regiao');
        this.pegarDadosPorRegiao(this.formulario.getRawValue().nomeRegiao);
        break;
      default:
        console.log(`Opção inexistente`);
        break;
    }
  }

  importarBase() {
    this.carregando = true;
    this.importarCsvService.criar().subscribe((result) => {
      this.carregando = false;
      this.pegarDados();
    });
  }

  criarFormulario() {
    this.formulario = this.formBuilder.group({
      nomeRegiao: [],
      nomeMunicipio: [],
      nomeBandeira: [],

      mediaPrecoCombustivel: [{ value: '', disabled: true }],
      mediaPrecoCompraCombustivel: [{ value: '', disabled: true }],

      mediaPrecoCombustivelBandeira: [{ value: '', disabled: true }],
      mediaPrecoCompraCombustivelBandeira: [{ value: '', disabled: true }],
    });
  }

  pegarDados() {
    this.pegarMediaPrecoComb();
    this.regiaoService.pegarDados().subscribe((result) => {
      this.listaRegioes = result.data as Array<String>;
    });
    this.servicoBandeira.pegarDados().subscribe((result) => {
      this.listaBandeiras = result.data as Array<String>;
    });
  }

  pegarDadosPorRegiao(siglaRegiao: string) {
    this.regiaoService
      .pegarRecursoAlternativo(
        { siglaRegiao, pag: this.config.currentPage },
        'regiao/buscarDadosPorRegiao'
      )
      .subscribe((result) => {
        this.items = result.data.content as Array<any>;
        this.config.totalItems = +result.data.totalElements;
      });
  }

  pegarMediaPrecoComb(nomeMunicipio?: string) {
    this.servicoMunicipio.pegarDados({ nomeMunicipio }).subscribe((result) => {
      if (!nomeMunicipio) {
        this.listaMunicipios = result.data as Array<String>;
      } else {
        this.formulario.patchValue({
          mediaPrecoCombustivel: (+result.data[0]).toFixed(4),
        });
      }
    });
  }

  pegarMediaPrecoCompra(nomeMunicipio) {
    this.servicoMunicipio
      .pegarMediaPrecoCompra({ nomeMunicipio })
      .subscribe((result) => {
        this.formulario.patchValue({
          mediaPrecoCompraCombustivel: (+result[0]).toFixed(4),
        });
      });
  }

  pegarDadosPorDistribuidora(viaBotao?: boolean) {
    if (viaBotao) {
      this.config.currentPage = 1;
      this.filtroAtivo = 'distribuidora';
    }

    this.servico
      .pegarRecursoAlternativo(
        {
          nomeBandeira: this.formulario.getRawValue().nomeBandeira,
          pag: this.config.currentPage,
        },
        'bandeira/buscarDadosPorBandeira'
      )
      .subscribe((result) => {
        this.items = result.data.content as Array<any>;
        this.config.totalItems = +result.data.totalElements;
      });
  }

  pegarDadosPorData(viaBotao?: boolean) {
    if (viaBotao) {
      this.config.currentPage = 1;
      this.filtroAtivo = 'data';
    }

    this.servico
      .pegarRecursoAlternativo(
        { tipo: 'DESC', pag: this.config.currentPage },
        'bandeira/buscarDadosPorData'
      )
      .subscribe((result) => {
        this.items = result.data.content as Array<any>;
        this.config.totalItems = +result.data.totalElements;
      });
  }

  pegarMediasPrecoPorBandeira(nomeBandeira) {
    this.servico
      .pegarRecursoAlternativo(
        { nomeBandeira },
        'bandeira/buscarMediaPrecoCompra'
      )
      .subscribe((result) => {
        this.formulario.patchValue({
          mediaPrecoCompraCombustivelBandeira: (+result.data[0]).toFixed(4),
        });
      });
    this.servico
      .pegarRecursoAlternativo(
        { nomeBandeira },
        'bandeira/buscarMediaPrecoVenda'
      )
      .subscribe((result) => {
        this.formulario.patchValue({
          mediaPrecoCombustivelBandeira: (+result.data[0]).toFixed(4),
        });
      });
  }
}
