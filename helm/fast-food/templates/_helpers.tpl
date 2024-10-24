{{- define "fast-food.labels" }}
app.kubernetes.io/name: {{.Chart.Name}}
app.kubernetes.io/instance: {{.Release.Name}}
app.kubernetes.io/version: {{.Chart.AppVersion}}
app.kubernetes.io/managed-by: {{.Release.Service}}
{{- end}}
