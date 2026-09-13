import re

with open('build.gradle', 'r') as f:
    content = f.read()

# Add mixin block
if 'mixin {' not in content:
    content = content.replace('minecraft {', '''
mixin {
    add sourceSets.main, "futureshops_tacz_compat.refmap.json"
    config "futureshops_tacz_compat.mixins.json"
}

minecraft {''')

with open('build.gradle', 'w') as f:
    f.write(content)
