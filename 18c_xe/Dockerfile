FROM oraclelinux:7-slim

ENV ORACLE_BASE /opt/oracle
ENV ORACLE_HOME ${ORACLE_BASE}/product/18c/dbhomeXE
ENV ORACLE_SID=XE
ENV NLS_CHARACTERSET=UTF8
ENV SGA_TARGET=1536M

#TODO: do something lighter than preinstall
RUN yum install -y oracle-database-preinstall-18c strace ; rm -rf /var/cache/yum
RUN mkdir -p ${ORACLE_BASE} ${ORACLE_HOME} ; chown -R oracle:oinstall ${ORACLE_BASE} ${ORACLE_HOME}
USER oracle
WORKDIR ${ORACLE_HOME}

################################################################################################################
# sqlplus
################################################################################################################
COPY createUserAndTS.sh $ORACLE_HOME
COPY health_check.sh $ORACLE_HOME
RUN mkdir -p bin
COPY --from=oracle/database:18.4.0-xe --chown=oracle:oinstall\
  ${ORACLE_HOME}/bin/sqlplus\
  bin/
RUN mkdir -p lib
COPY --from=oracle/database:18.4.0-xe --chown=oracle:oinstall\
  ${ORACLE_HOME}/lib/libsqlplus.so\
  ${ORACLE_HOME}/lib/libclntsh.so.18.1\
  ${ORACLE_HOME}/lib/libclntshcore.so.18.1\
  ${ORACLE_HOME}/lib/libmql1.so\
  ${ORACLE_HOME}/lib/libipc1.so\
  ${ORACLE_HOME}/lib/libnnz18.so\
  ${ORACLE_HOME}/lib/libons.so\
  lib/
RUN mkdir -p sqlplus/mesg
COPY --from=oracle/database:18.4.0-xe --chown=oracle:oinstall\
  ${ORACLE_HOME}/sqlplus/mesg/sp1us.msb\
  ${ORACLE_HOME}/sqlplus/mesg/sp2us.msb\
  sqlplus/mesg/
RUN mkdir -p rdbms/mesg
COPY --from=oracle/database:18.4.0-xe --chown=oracle:oinstall\
  ${ORACLE_HOME}/rdbms/mesg/ocius.msb\
  ${ORACLE_HOME}/rdbms/mesg/oraus.msb\
  ${ORACLE_HOME}/rdbms/mesg/diaus.msb\
  rdbms/mesg/
RUN mkdir -p oracore/zoneinfo
COPY --from=oracle/database:18.4.0-xe --chown=oracle:oinstall\
  ${ORACLE_HOME}/oracore/zoneinfo/*.dat\
  oracore/zoneinfo/
RUN du -h ${ORACLE_BASE} | sort -u

################################################################################################################
# oracle core
################################################################################################################
#TODO# move audit and dbs (rooh)
RUN mkdir -p rdbms/audit dbs log rdbms/log
COPY --from=oracle/database:18.4.0-xe --chown=oracle:oinstall\
  ${ORACLE_HOME}/bin/oracle\
  bin/
#RUN strip --remove-section=.comment ${ORACLE_HOME}/bin/oracle
COPY --from=oracle/database:18.4.0-xe --chown=oracle:oinstall\
  ${ORACLE_HOME}/lib/libodm18.so\
  ${ORACLE_HOME}/lib/libofs.so\
  ${ORACLE_HOME}/lib/libcell18.so\
  ${ORACLE_HOME}/lib/libskgxp18.so\
  ${ORACLE_HOME}/lib/libskjcx18.so\
  ${ORACLE_HOME}/lib/libclsra18.so\
  ${ORACLE_HOME}/lib/libdbcfg18.so\
  ${ORACLE_HOME}/lib/libhasgen18.so\
  ${ORACLE_HOME}/lib/libskgxn2.so\
  ${ORACLE_HOME}/lib/libocr18.so\
  ${ORACLE_HOME}/lib/libocrb18.so\
  ${ORACLE_HOME}/lib/libocrutl18.so\
  ${ORACLE_HOME}/lib/libmkl_rt.so\
  ${ORACLE_HOME}/lib/libasmclntsh18.so\
  lib/
RUN mkdir -p oracore/mesg
COPY --from=oracle/database:18.4.0-xe --chown=oracle:oinstall\
  ${ORACLE_HOME}/oracore/mesg/lrmus.msb\
  oracore/mesg
RUN echo -e 'db_name=XE' >> ${ORACLE_HOME}/dbs/initXE.ora
RUN echo -e 'SHARED_POOL_SIZE=400M' >> ${ORACLE_HOME}/dbs/initXE.ora
#RUN echo -e 'sga_target=1536M' >> ${ORACLE_HOME}/dbs/initXE.ora

################################################################################################################
# oracle rdbms
################################################################################################################
RUN mkdir -p ldap/mesg
COPY --from=oracle/database:18.4.0-xe --chown=oracle:oinstall\
  ${ORACLE_HOME}/ldap/mesg/ldapus.msb\
  ldap/mesg/
RUN mkdir -p network/mesg
COPY --from=oracle/database:18.4.0-xe --chown=oracle:oinstall\
  ${ORACLE_HOME}/network/mesg/tnsus.msb\
  network/mesg
RUN mkdir -p nls/data
COPY --from=oracle/database:18.4.0-xe --chown=oracle:oinstall\
  ${ORACLE_HOME}/nls/data\
  nls/data
RUN mkdir -p rdbms/admin
COPY --from=oracle/database:18.4.0-xe --chown=oracle:oinstall\
  ${ORACLE_HOME}/rdbms/admin/sql.bsq\
  ${ORACLE_HOME}/rdbms/admin/dcore.bsq\
  ${ORACLE_HOME}/rdbms/admin/dsqlddl.bsq\
  ${ORACLE_HOME}/rdbms/admin/dmanage.bsq\
  ${ORACLE_HOME}/rdbms/admin/dplsql.bsq\
  ${ORACLE_HOME}/rdbms/admin/dtxnspc.bsq\
  ${ORACLE_HOME}/rdbms/admin/dfmap.bsq\
  ${ORACLE_HOME}/rdbms/admin/denv.bsq\
  ${ORACLE_HOME}/rdbms/admin/drac.bsq\
  ${ORACLE_HOME}/rdbms/admin/dsec.bsq\
  ${ORACLE_HOME}/rdbms/admin/doptim.bsq\
  ${ORACLE_HOME}/rdbms/admin/dobj.bsq\
  ${ORACLE_HOME}/rdbms/admin/djava.bsq\
  ${ORACLE_HOME}/rdbms/admin/dpart.bsq\
  ${ORACLE_HOME}/rdbms/admin/drep.bsq\
  ${ORACLE_HOME}/rdbms/admin/daw.bsq\
  ${ORACLE_HOME}/rdbms/admin/dsummgt.bsq\
  ${ORACLE_HOME}/rdbms/admin/dtools.bsq\
  ${ORACLE_HOME}/rdbms/admin/dexttab.bsq\
  ${ORACLE_HOME}/rdbms/admin/ddm.bsq\
  ${ORACLE_HOME}/rdbms/admin/dlmnr.bsq\
  ${ORACLE_HOME}/rdbms/admin/ddst.bsq\
  ${ORACLE_HOME}/rdbms/admin/dfba.bsq\
  ${ORACLE_HOME}/rdbms/admin/dpstdy.bsq\
  ${ORACLE_HOME}/rdbms/admin/drupg.bsq\
  ${ORACLE_HOME}/rdbms/admin/dtlog.bsq\
  ${ORACLE_HOME}/rdbms/admin/dmisc.bsq\
  ${ORACLE_HOME}/rdbms/admin/dhcs.bsq\
  rdbms/admin/
RUN du -h ${ORACLE_HOME} | sort -h
RUN echo -e 'alias "sqlplus=cd ${ORACLE_BASE}/diag/rdbms/*/${ORACLE_SID}/trace ; cd - ; ${ORACLE_HOME}/bin/sqlplus"' > ~/.bashrc
RUN echo -e 'whenever sqlerror exit failure;\n startup nomount;\n create database CHARACTER SET UTF8;\ncreate spfile from pfile;' | ORACLE_BASE=${ORACLE_BASE} LD_LIBRARY_PATH=${ORACLE_HOME}/lib ORACLE_HOME=${ORACLE_HOME} ORACLE_SID=${ORACLE_SID} ${ORACLE_HOME}/bin/sqlplus / as sysdba

################################################################################################################
# oracle catalog
################################################################################################################
# simpler to take all files, that's only 20MB more than the required ones
COPY --from=oracle/database:18.4.0-xe --chown=oracle:oinstall\
  ${ORACLE_HOME}/rdbms/admin/*.sql\
  ${ORACLE_HOME}/rdbms/admin/*.plb\
  rdbms/admin/
RUN echo -e 'startup;\n set echo on termout off\n spool /var/tmp/catalog.lst\n @?/rdbms/admin/catalog' | ORACLE_BASE=${ORACLE_BASE} LD_LIBRARY_PATH=${ORACLE_HOME}/lib ORACLE_HOME=${ORACLE_HOME} ORACLE_SID=${ORACLE_SID} ${ORACLE_HOME}/bin/sqlplus / as sysdba
RUN du -h ${ORACLE_BASE} | sort -u

################################################################################################################
# oracle catproc
################################################################################################################
RUN mkdir -p rdbms/xml
# prvt_emx.register_files(TRUE) needs rdbms/xml
COPY --from=oracle/database:18.4.0-xe --chown=oracle:oinstall\
  ${ORACLE_HOME}/rdbms/xml\
  rdbms/xml
RUN echo -e 'startup;\n set echo on termout off\n spool /var/tmp/catproc.lst\n @?/rdbms/admin/catproc' | ORACLE_BASE=${ORACLE_BASE} LD_LIBRARY_PATH=${ORACLE_HOME}/lib ORACLE_HOME=${ORACLE_HOME} ORACLE_SID=${ORACLE_SID} ${ORACLE_HOME}/bin/sqlplus / as sysdba
RUN du -h ${ORACLE_BASE} | sort -u
RUN $ORACLE_HOME/createUserAndTS.sh

################################################################################################################
# listener
################################################################################################################
RUN mkdir -p bin
COPY --from=oracle/database:18.4.0-xe --chown=oracle:oinstall\
  ${ORACLE_HOME}/bin/lsnrctl\
  ${ORACLE_HOME}/bin/tnslsnr\
  bin/
#TODO# if we add an external volume, need to move all ?/dbs to it at start (and symlink). clonedb may help to have onlyy sparse files
CMD  echo -e "sga_target=${SGA_TARGET}" >> ${ORACLE_HOME}/dbs/initXE.ora; ${ORACLE_HOME}/bin/lsnrctl start ; echo create spfile from pfile\; | ${ORACLE_HOME}/bin/sqlplus / as sysdba ;echo startup | ${ORACLE_HOME}/bin/sqlplus / as sysdba ; ${ORACLE_HOME}/bin/lsnrctl status ; tail -f $ORACLE_BASE/diag/rdbms/*/*/trace/alert*.log
HEALTHCHECK --interval=2s  --timeout=1s --retries=20 CMD $ORACLE_HOME/health_check.sh
EXPOSE 1521
