#!/usr/bin/python
import subprocess, os, sys
code_dir = "code"
title = "3N1?M4 Team Reference"

def get_sections():
    sections = []
    section_name = None
    with open('contents.txt', 'r') as f:
        for line in f:
            if '#' in line: line = line[:line.find('#')]
            line = line.strip()
            if len(line) == 0: continue
            if line[0] == '[':
                section_name = line[1:-1]
                subsections = []
                if section_name is not None:
                    sections.append((section_name, subsections))
            else:
                tmp = line.split('\t', 1)
                if len(tmp) == 1:
                    raise ValueError('Subsection parse error: %s' % line)
                filename = tmp[0]
                subsection_name = tmp[1]
                if section_name is None:
                    raise ValueError('Subsection given without section')
                subsections.append((filename, subsection_name))
    return sections

def get_style(filename):
    ext = filename.lower().split('.')[-1]
    if ext in ['c', 'cc', 'cpp', 'h']:
        return 'cpp'
    elif ext in ['java']:
        return 'java'
    elif ext in ['py']:
        return 'py'
    elif ext in ['sh']:
        return 'sh'
    elif ext in ['tex']:
        return 'tex'
    else:
        return 'txt'

# TODO: check if this is everything we need
def texify(s):
    #s = s.replace('\'', '\\\'')
    #s = s.replace('\"', '\\\"')
    return s

def get_tex(sections):
    tex = ''
    for (section_name, subsections) in sections:
        tex += '\\section{%s}\n' % texify(section_name)
        for (filename, subsection_name) in subsections:
            tex += '\\subsection{%s}\n' % texify(subsection_name)
            
            style = get_style(filename)
            
            if style == 'tex':
                tex += '\\input{%s/%s}\n' % (code_dir, filename)
            else:
                tex += '\\raggedbottom\\lstinputlisting[style=%s]{%s/%s}\n' % (style, code_dir, filename)
            # tex += '\\hrulefill\n'
        # tex += '\\newpage\n'
        tex += '\n'
    return tex


def tabs_to_spaces():
    for File in os.listdir('./code'):
        f = open('./code/' + File)
        L = f.readlines()
        f.close()

        f = open('./code/' + File, 'w')
        for line in L: 
            for c in line:
                if c != '\t':
                    f.write(c)
                else:
                    f.write('    ')     

if __name__ == "__main__":
    tabs_to_spaces()
    sections = get_sections()
    tex = get_tex(sections)
    with open('contents.tex', 'w') as f:
        f.write(tex)
    # latexmk_options = ["latexmk", "-pdf", "-f", "-silent", "notebook.tex"]
    latexmk_options = ["latexmk", "-pdf", "-f", "handbook.tex"]
    subprocess.call(latexmk_options)





# ~ #!/usr/bin/python
# ~ import subprocess, os, sys
# ~ code_dir = "code"
# ~ title = "3N1?M4 Team Reference"

# ~ # TODO: check if this is everything we need
# ~ def texify(s):
    # ~ #s = s.replace('\'', '\\\'')
    # ~ #s = s.replace('\"', '\\\"')
    # ~ return s

# ~ if __name__ == "__main__":
    # ~ latexmk_options = ["latexmk", "-pdf", "notebook.tex"]
    # ~ subprocess.call(latexmk_options)
# ~ #    subprocess.call(["okular", "notebook.pdf"])
