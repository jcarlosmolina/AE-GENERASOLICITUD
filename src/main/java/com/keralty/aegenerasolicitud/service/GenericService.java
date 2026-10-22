package com.keralty.aegenerasolicitud.service;

import com.keralty.aegenerasolicitud.repository.AnexoMatJpaRepository;
import com.keralty.aegenerasolicitud.repository.AnexoNeoJpaRepository;
import com.keralty.aegenerasolicitud.repository.ConfiguracionANSJpaRepository;
import com.keralty.aegenerasolicitud.repository.FamiliaNovedadJpaRepository;
import com.keralty.aegenerasolicitud.repository.NovedadJpaRepository;
import com.keralty.aegenerasolicitud.repository.ReportesDominioJpaRepository;
import com.keralty.aegenerasolicitud.repository.SolicitudNovedadJpaRepository;
import com.keralty.aegenerasolicitud.repository.UsuarioNovedadJpaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Transactional
@ApplicationScoped
public class GenericService {

    private AnexoMatJpaRepository anexoMatJpaRepository;

    private AnexoNeoJpaRepository anexoNeoJpaRepository;

    private NovedadJpaRepository novedadJpaRepository;

    private SolicitudNovedadJpaRepository solicitudNovedadJpaRepository;

    private FamiliaNovedadJpaRepository familiaNovedadJpaRepository;

    private UsuarioNovedadJpaRepository usuarioNovedadJpaRepository;

    private ConfiguracionANSJpaRepository configuracionANSJpaRepository;

    private ReportesDominioJpaRepository reportesDominioJpaRepository;
	
	@Inject
	public GenericService(
            AnexoMatJpaRepository anexoMatJpaRepository,
            AnexoNeoJpaRepository anexoNeoJpaRepository,
            NovedadJpaRepository novedadJpaRepository,
            SolicitudNovedadJpaRepository solicitudNovedadJpaRepository,
            FamiliaNovedadJpaRepository familiaNovedadJpaRepository,
            UsuarioNovedadJpaRepository usuarioNovedadJpaRepository,
            ConfiguracionANSJpaRepository configuracionANSJpaRepository,
            ReportesDominioJpaRepository reportesDominioJpaRepository
			) {
        this.anexoMatJpaRepository = anexoMatJpaRepository;
        this.anexoNeoJpaRepository = anexoNeoJpaRepository;
        this.novedadJpaRepository = novedadJpaRepository;
        this.solicitudNovedadJpaRepository = solicitudNovedadJpaRepository;
        this.familiaNovedadJpaRepository = familiaNovedadJpaRepository;
        this.usuarioNovedadJpaRepository = usuarioNovedadJpaRepository;
        this.configuracionANSJpaRepository = configuracionANSJpaRepository;
        this.reportesDominioJpaRepository = reportesDominioJpaRepository;
	}

	public AnexoMatJpaRepository getAnexoMatJpaRepository() {
		return this.anexoMatJpaRepository;
	}
	public AnexoNeoJpaRepository getAnexoNeoJpaRepository() {
		return this.anexoNeoJpaRepository;
	}
	public NovedadJpaRepository getNovedadJpaRepository() {
		return this.novedadJpaRepository;
	}
	public SolicitudNovedadJpaRepository getSolicitudNovedadJpaRepository() {
		return this.solicitudNovedadJpaRepository;
	}
	public FamiliaNovedadJpaRepository getFamiliaNovedadJpaRepository() {
		return this.familiaNovedadJpaRepository;
	}
	public UsuarioNovedadJpaRepository getUsuarioNovedadJpaRepository() {
		return this.usuarioNovedadJpaRepository;
	}
	public ConfiguracionANSJpaRepository getConfiguracionANSJpaRepository() {
		return this.configuracionANSJpaRepository;
	}
	public ReportesDominioJpaRepository getReportesDominioJpaRepository() {
		return this.reportesDominioJpaRepository;
	}
}
