package io.diego.tech.repository;

import io.diego.lib.spring.data.service.generic.repository.GenericRepository;
import io.diego.tech.model.Pessoa;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends GenericRepository<Pessoa, Long> {

	// default Noticia findOneWithFetch(Predicate predicate) {
	// JPAQuery<Noticia> jpaQuery = new JPAQuery<>(getEntityMananger());
	// jpaQuery.from(noticia);
	// jpaQuery.innerJoin(noticia.edital, edital).fetchJoin();
	// jpaQuery.innerJoin(edital.concurso, concurso).fetchJoin();
	//
	// jpaQuery.leftJoin(noticia.noticiaByArquivos, noticiaByArquivo).fetchJoin();
	// jpaQuery.leftJoin(noticiaByArquivo.arquivo, arquivo).fetchJoin();
	// jpaQuery.leftJoin(arquivo.noticiaByArquivos, noticiaByArquivo).fetchJoin();
	//
	// jpaQuery.where(predicate);
	// return jpaQuery.fetchOne();
	// }
	//
	// default List<Noticia> findAllWithFetch(Predicate predicate, Pageable pageable) {
	// JPAQuery<NoticiaDTO> query = new JPAQuery<>(getEntityMananger());
	// StringExpression titulo = noticia.titulo.substring(0, 70);
	// StringExpression descricaoNoticia = noticia.descricao.substring(0, 70);
	// query.select(new QNoticiaDTO(noticia.id, noticia.dataHora, titulo, descricaoNoticia, edital.descricao, noticia.publicado));
	// query.from(noticia).fetchAll();
	// query.innerJoin(noticia.edital, edital);
	// query.innerJoin(edital.concurso, concurso);
	// query.where(predicate);
	// applyPagination(query, pageable);
	// List<NoticiaDTO> list = query.fetch();
	// return list.stream().map(NoticiaDTO::toEntity).collect(Collectors.toList());
	// }
	//
	// default long countAllWithFetch(Predicate predicate) {
	// JPAQuery<Noticia> query = new JPAQuery<>(getEntityMananger());
	// query.from(noticia);
	// query.innerJoin(noticia.edital, edital);
	// query.where(predicate);
	// return query.fetchCount();
	// }
	//
	// @VhTransactional
	// default void alterarStatusPublicacao(Noticia entity) {
	// JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(getEntityMananger(), noticia);
	// jpaUpdateClause.set(noticia.publicado, entity.isPublicado());
	// jpaUpdateClause.set(noticia.dataHoraPublicado, entity.getDataHoraPublicado());
	// jpaUpdateClause.where(noticia.id.eq(entity.getId()));
	// jpaUpdateClause.execute();
	// }
	//
	// default List<NoticiaSimplesDTO> getListaSimplesBySituacaoIsTrueAndPublicadoIsTrueAndEditalId(Long editalId) {
	// JPAQuery<NoticiaSimplesDTO> query = new JPAQuery<>(getEntityMananger());
	// query.select(new QNoticiaSimplesDTO(noticia.id, noticia.titulo, noticia.descricao));
	// query.from(noticia);
	// query.where(noticia.edital.id.eq(editalId));
	// query.where(noticia.situacao.isTrue());
	// query.where(noticia.publicado.isTrue());
	// query.orderBy(noticia.dataHora.desc());
	// return query.fetch();
	// }
	//
	// default List<ArquivoDTO> getListaArquivoByNoticiaId(Long noticiaId) {
	// JPAQuery<ArquivoDTO> query = new JPAQuery<>(getEntityMananger());
	// query.select(new QArquivoDTO(arquivo.id, arquivo.titulo, arquivo.nomeOriginal, arquivo.hashMd5));
	// query.from(noticiaByArquivo);
	// query.innerJoin(noticiaByArquivo.arquivo, arquivo);
	// query.where(noticiaByArquivo.noticia.id.eq(noticiaId));
	// return query.fetch();
	// }

}
