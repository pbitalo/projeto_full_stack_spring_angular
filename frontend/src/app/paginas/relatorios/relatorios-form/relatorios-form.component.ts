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
        this.pegarDadosPorRegiao(valor);
      }
    });
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
      this.listaRegioes = result as Array<String>;
    });
    this.servicoBandeira.pegarDados().subscribe((result) => {
      this.listaBandeiras = result as Array<String>;
    });
  }

  pegarDadosPorRegiao(siglaRegiao: string) {
    this.regiaoService
      .pegarRecursoAlternativo({ siglaRegiao }, 'regiao/buscarDadosPorRegiao')
      .subscribe((result) => {
        this.items = result.splice(0, 40);
      });
  }

  pegarMediaPrecoComb(nomeMunicipio?: string) {
    this.servicoMunicipio.pegarDados({ nomeMunicipio }).subscribe((result) => {
      if (!nomeMunicipio) {
        this.listaMunicipios = result as Array<String>;
      } else {
        this.formulario.patchValue({
          mediaPrecoCombustivel: (+result[0]).toFixed(4),
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

  pegarDadosPorDistribuidora() {
    this.servico
      .pegarRecursoAlternativo(
        { nomeBandeira: this.formulario.getRawValue().nomeBandeira },
        'bandeira/buscarDadosPorBandeira'
      )
      .subscribe((result) => {
        this.items = result;
      });
  }

  pegarDadosPorData() {
    this.servico
      .pegarRecursoAlternativo({ tipo: 'DESC' }, 'bandeira/buscarDadosPorData')
      .subscribe((result) => {
        this.items = result;
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
          mediaPrecoCompraCombustivelBandeira: (+result[0]).toFixed(4),
        });
      });
    this.servico
      .pegarRecursoAlternativo(
        { nomeBandeira },
        'bandeira/buscarMediaPrecoVenda'
      )
      .subscribe((result) => {
        this.formulario.patchValue({
          mediaPrecoCombustivelBandeira: (+result[0]).toFixed(4),
        });
      });
  }
}
