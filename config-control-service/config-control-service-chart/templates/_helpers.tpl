{{/*
Expand the name of the chart.
*/}}
{{- define "_helpers-config-control-service.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "_helpers-config-control-service.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "_helpers-config-control-service.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Volumes
*/}}
{{- define "_helpers-config-control-service.volume-mounts.application-yml" -}}
- name: {{ include "_helpers-config-control-service.name" . }}-application
  mountPath: /tmp/conf/application.yml
  subPath: application.yml
{{- end -}}

{{- define "_helpers-config-control-service.volumes.application-yml" -}}
- name: {{ include "_helpers-config-control-service.name" . }}-application
  configMap:
    name: {{ include "_helpers-config-control-service.name" . }}
    items:
      - key: application.yml
        path: application.yml
{{- end -}}

{{- define "_helpers-config-control-service.volume-mounts.secret-yml" -}}
- name: {{ include "_helpers-config-control-service.name" . }}-secret
  mountPath: /tmp/secret/secret.yml
  subPath: secret.yml
{{- end -}}

{{- define "_helpers-config-control-service.volumes.secret-yml" -}}
- name: {{ include "_helpers-config-control-service.name" . }}-secret
  secret:
    secretName: {{ include "_helpers-config-control-service.name" . }}
    items:
      - key: secret.yml
        path: secret.yml
{{- end -}}

{{- define "_helpers-config-control-service.service-monitor.endpoints" -}}
- port: http
  path: /actuator/prometheus
{{- end -}}