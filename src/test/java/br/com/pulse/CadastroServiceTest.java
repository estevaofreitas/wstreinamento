package br.com.ithappens.contabil.rest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

import br.com.ithappens.dominio.cadastro.gmcore.Fornecedor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import br.com.ithappens.contabil.rest.mapper.CadastroMapper;
import br.com.ithappens.dominio.cadastro.gmcore.Cliente;

public class CadastroServiceTest {

  private CadastroMapper   cadastroMapper;
  private ICadastroService cadastroService;

  @Before
  public void setUp() {
    cadastroMapper = mock(CadastroMapper.class);
    cadastroService = new CadastroService(cadastroMapper);
  }

  @Test
  public void deveInvocarMetodoRecuperarFilialDeCadastroMapper() {
    Long cnpj = 123345345L;
    ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
    cadastroService.recuperarFilial(cnpj);
    verify(cadastroMapper, times(1)).recuperarFilial(captor.capture());
    verifyNoMoreInteractions(cadastroMapper);
    assertSame(cnpj, captor.getValue());
  }

  @Test
  public void deveRetornarFornecedorNuloQuandoCnpjForInvalido() {
    String cnpj = "";
    Fornecedor fornecedor = cadastroService.recuperarFornecedor(cnpj);
    Assert.assertNull(fornecedor);
  }

  @Test
  public void deveInvocarMetodoRecuperarFornecedorApenasUmaVez() {
    String cnpj = "96552798000153";
    Fornecedor mockFornecedor = mock(Fornecedor.class);
    when(cadastroMapper.recuperarFornecedor(cnpj)).thenReturn(mockFornecedor);
    Fornecedor fornecedor = cadastroService.recuperarFornecedor(cnpj);
    verify(cadastroMapper, times(1)).recuperarFornecedor(cnpj);
    Assert.assertSame(mockFornecedor, fornecedor);
  }

  @Test
  public void deveInvocarMetodoRecuperarFornecedorDuasVezes() {
    String cnpj = "06552798000153";
    String cnpjPosConversaoLong = "6552798000153";
    Fornecedor mockFornecedor = mock(Fornecedor.class);
    when(cadastroMapper.recuperarFornecedor(cnpj)).thenReturn(null);
    when(cadastroMapper.recuperarFornecedor(cnpjPosConversaoLong)).thenReturn(mockFornecedor);
    Fornecedor fornecedor = cadastroService.recuperarFornecedor(cnpj);
    verify(cadastroMapper, times(1)).recuperarFornecedor(cnpj);
    verify(cadastroMapper, times(1)).recuperarFornecedor(cnpjPosConversaoLong);
    Assert.assertSame(mockFornecedor, fornecedor);
  }

  @Test
  public void deveInvocarMetodoRecuperarSerieDocumentoDeCadastroMapper() {
    String serie = "01";
    String modelo = "55";
    ArgumentCaptor<String> captorSerie = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> captorModelo = ArgumentCaptor.forClass(String.class);
    cadastroService.recuperarSerieDocumento(serie, modelo);
    verify(cadastroMapper, times(1)).recuperarSerieDocumento(captorSerie.capture(), captorModelo.capture());
    verifyNoMoreInteractions(cadastroMapper);
    assertSame(serie, captorSerie.getValue());
  }

  @Test
  public void deveRetornarClienteNuloQuandoParametroForNulo() {
    String cnpj = null;
    Cliente cliente = cadastroService.recuperarCliente(cnpj);
    assertNull(cliente);
  }

  @Test
  public void recuperarCliente(){
    String cnpj = "123345345";
    ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
    cadastroService.recuperarCliente(cnpj);
    verify(cadastroMapper, atLeast(1)).recuperarCliente(captor.capture());
    assertEquals(cnpj, captor.getValue());
  }
}