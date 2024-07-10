{{/*
Expand the name of the chart.
*/}}
{{- define "_helpers-report-generator-service.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "_helpers-report-generator-service.fullname" -}}
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
{{- define "_helpers-report-generator-service.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Volumes
*/}}
{{- define "_helpers-report-generator-service.volume-mounts.application-yml" -}}
- name: {{ include "_helpers-report-generator-service.name" . }}-application
  mountPath: /tmp/conf/application.yml
  subPath: application.yml
{{- end -}}

{{- define "_helpers-report-generator-service.volumes.application-yml" -}}
- name: {{ include "_helpers-report-generator-service.name" . }}-application
  configMap:
    name: {{ include "_helpers-report-generator-service.name" . }}
    items:
      - key: application.yml
        path: application.yml
{{- end -}}

{{- define "_helpers-report-generator-service.service-monitor.endpoints" -}}
- port: http
  path: /actuator/prometheus
{{- end -}}