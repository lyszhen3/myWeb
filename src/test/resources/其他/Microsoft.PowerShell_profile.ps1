oh-my-posh init pwsh --config "$env:POSH_THEMES_PATH\multiverse-neon.omp.json" | Invoke-Expression
Import-Module Terminal-Icons
function wt { wt.exe -d $PWD @args }
# oh-my-posh 初始化后再包装 prompt
$_ompPrompt = $function:prompt
function prompt {
    & $_ompPrompt
    $leaf = Split-Path (Get-Location) -Leaf
    Write-Host -NoNewline "`e]0;$leaf`a"
}
