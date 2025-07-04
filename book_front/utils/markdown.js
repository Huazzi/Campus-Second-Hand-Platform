/**
 * 优化后的Markdown解析器，适用于微信小程序
 * 支持常用的Markdown语法，增加了错误处理和性能优化
 */

/**
 * 解析Markdown文本为富文本节点数组
 * @param {string} markdown - Markdown文本
 * @returns {Array} 富文本节点数组
 */
function parseMarkdown(markdown) {
  if (!markdown || typeof markdown !== 'string') {
    return [{ type: 'text', text: '' }];
  }

  const nodes = [];
  const lines = markdown.split('\n');
  let inCodeBlock = false;
  let codeBlockContent = [];
  let codeBlockLang = '';
  
  for (let i = 0; i < lines.length; i++) {
    const line = lines[i];
    const trimmedLine = line.trim();
    
    // 处理代码块
    if (trimmedLine.startsWith('```')) {
      if (!inCodeBlock) {
        // 开始代码块
        inCodeBlock = true;
        codeBlockLang = trimmedLine.substring(3).trim();
        codeBlockContent = [];
      } else {
        // 结束代码块
        inCodeBlock = false;
        nodes.push({
          name: 'div',
          attrs: { 
            class: 'md-code-block',
            'data-lang': codeBlockLang 
          },
          children: [{ type: 'text', text: codeBlockContent.join('\n') }]
        });
        codeBlockContent = [];
        codeBlockLang = '';
      }
      continue;
    }
    
    // 如果在代码块中，直接添加内容
    if (inCodeBlock) {
      codeBlockContent.push(line);
      continue;
    }
    
    // 处理标题（支持1-6级）
    const headerMatch = line.match(/^(#{1,6})\s+(.+)$/);
    if (headerMatch) {
      const level = headerMatch[1].length;
      const text = headerMatch[2];
      const className = level <= 3 ? `md-h${level}` : 'md-h3';
      nodes.push({
        name: 'div',
        attrs: { class: className },
        children: parseInlineMarkdown(text)
      });
      continue;
    }
    
    // 处理无序列表（支持多级缩进）
    const unorderedListMatch = line.match(/^(\s*)[-*+]\s+(.+)$/);
    if (unorderedListMatch) {
      const indent = unorderedListMatch[1].length;
      const text = unorderedListMatch[2];
      nodes.push({
        name: 'div',
        attrs: { 
          class: 'md-li',
          style: `margin-left: ${indent * 20}rpx;`
        },
        children: [
          { name: 'span', attrs: { class: 'md-bullet' }, children: [{ type: 'text', text: '• ' }] },
          ...parseInlineMarkdown(text)
        ]
      });
      continue;
    }
    
    // 处理有序列表（支持多级缩进）
    const orderedListMatch = line.match(/^(\s*)(\d+)\.\s+(.+)$/);
    if (orderedListMatch) {
      const indent = orderedListMatch[1].length;
      const number = orderedListMatch[2];
      const text = orderedListMatch[3];
      nodes.push({
        name: 'div',
        attrs: { 
          class: 'md-li',
          style: `margin-left: ${indent * 20}rpx;`
        },
        children: [
          { name: 'span', attrs: { class: 'md-number' }, children: [{ type: 'text', text: number + '. ' }] },
          ...parseInlineMarkdown(text)
        ]
      });
      continue;
    }
    
    // 处理引用（支持多级引用）
    const quoteMatch = line.match(/^(>+)\s*(.*)$/);
    if (quoteMatch) {
      const level = quoteMatch[1].length;
      const text = quoteMatch[2];
      nodes.push({
        name: 'div',
        attrs: { 
          class: 'md-blockquote',
          style: `margin-left: ${(level - 1) * 20}rpx;`
        },
        children: parseInlineMarkdown(text)
      });
      continue;
    }
    
    // 处理分割线
    if (/^[-*_]{3,}$/.test(trimmedLine)) {
      nodes.push({
        name: 'div',
        attrs: { class: 'md-hr' }
      });
      continue;
    }
    
    // 处理空行
    if (trimmedLine === '') {
      nodes.push({
        name: 'div',
        attrs: { class: 'md-br' }
      });
      continue;
    }
    
    // 处理普通段落
    nodes.push({
      name: 'div',
      attrs: { class: 'md-p' },
      children: parseInlineMarkdown(line)
    });
  }
  
  // 如果最后还在代码块中，添加剩余内容
  if (inCodeBlock && codeBlockContent.length > 0) {
    nodes.push({
      name: 'div',
      attrs: { class: 'md-code-block' },
      children: [{ type: 'text', text: codeBlockContent.join('\n') }]
    });
  }
  
  return nodes;
}

/**
 * 解析行内Markdown语法
 * @param {string} text - 文本内容
 * @returns {Array} 富文本节点数组
 */
function parseInlineMarkdown(text) {
  if (!text) return [{ type: 'text', text: '' }];
  
  const nodes = [];
  let currentText = '';
  let i = 0;
  
  while (i < text.length) {
    const char = text[i];
    const nextChar = text[i + 1];
    const twoChars = text.substr(i, 2);
    
    try {
      // 处理粗体 **text**
      if (twoChars === '**') {
        if (currentText) {
          nodes.push({ type: 'text', text: currentText });
          currentText = '';
        }
        
        const endIndex = text.indexOf('**', i + 2);
        if (endIndex !== -1 && endIndex > i + 2) {
          const boldText = text.substring(i + 2, endIndex);
          nodes.push({
            name: 'span',
            attrs: { class: 'md-bold' },
            children: [{ type: 'text', text: boldText }]
          });
          i = endIndex + 2;
        } else {
          currentText += char;
          i++;
        }
      }
      // 处理斜体 *text* (确保不是粗体)
      else if (char === '*' && nextChar !== '*' && text[i - 1] !== '*') {
        if (currentText) {
          nodes.push({ type: 'text', text: currentText });
          currentText = '';
        }
        
        const endIndex = text.indexOf('*', i + 1);
        if (endIndex !== -1 && endIndex > i + 1) {
          const italicText = text.substring(i + 1, endIndex);
          nodes.push({
            name: 'span',
            attrs: { class: 'md-italic' },
            children: [{ type: 'text', text: italicText }]
          });
          i = endIndex + 1;
        } else {
          currentText += char;
          i++;
        }
      }
      // 处理删除线 ~~text~~
      else if (twoChars === '~~') {
        if (currentText) {
          nodes.push({ type: 'text', text: currentText });
          currentText = '';
        }
        
        const endIndex = text.indexOf('~~', i + 2);
        if (endIndex !== -1 && endIndex > i + 2) {
          const strikeText = text.substring(i + 2, endIndex);
          nodes.push({
            name: 'span',
            attrs: { class: 'md-strike' },
            children: [{ type: 'text', text: strikeText }]
          });
          i = endIndex + 2;
        } else {
          currentText += char;
          i++;
        }
      }
      // 处理行内代码 `code`
      else if (char === '`') {
        if (currentText) {
          nodes.push({ type: 'text', text: currentText });
          currentText = '';
        }
        
        const endIndex = text.indexOf('`', i + 1);
        if (endIndex !== -1 && endIndex > i + 1) {
          const codeText = text.substring(i + 1, endIndex);
          nodes.push({
            name: 'span',
            attrs: { class: 'md-code' },
            children: [{ type: 'text', text: codeText }]
          });
          i = endIndex + 1;
        } else {
          currentText += char;
          i++;
        }
      }
      // 处理链接 [text](url)
      else if (char === '[') {
        const linkEndIndex = text.indexOf('](', i);
        if (linkEndIndex !== -1) {
          const urlEndIndex = text.indexOf(')', linkEndIndex + 2);
          if (urlEndIndex !== -1) {
            if (currentText) {
              nodes.push({ type: 'text', text: currentText });
              currentText = '';
            }
            
            const linkText = text.substring(i + 1, linkEndIndex);
            const url = text.substring(linkEndIndex + 2, urlEndIndex);
            
            // 验证URL格式
            if (linkText && url) {
              nodes.push({
                name: 'span',
                attrs: { 
                  class: 'md-link',
                  'data-url': url
                },
                children: [{ type: 'text', text: linkText }]
              });
              i = urlEndIndex + 1;
            } else {
              currentText += char;
              i++;
            }
          } else {
            currentText += char;
            i++;
          }
        } else {
          currentText += char;
          i++;
        }
      }
      // 处理图片 ![alt](url)
      else if (twoChars === '![') {
        const altEndIndex = text.indexOf('](', i + 2);
        if (altEndIndex !== -1) {
          const urlEndIndex = text.indexOf(')', altEndIndex + 2);
          if (urlEndIndex !== -1) {
            if (currentText) {
              nodes.push({ type: 'text', text: currentText });
              currentText = '';
            }
            
            const altText = text.substring(i + 2, altEndIndex);
            const url = text.substring(altEndIndex + 2, urlEndIndex);
            
            nodes.push({
              name: 'span',
              attrs: { 
                class: 'md-image',
                'data-alt': altText,
                'data-url': url
              },
              children: [{ type: 'text', text: `[图片: ${altText}]` }]
            });
            i = urlEndIndex + 1;
          } else {
            currentText += char;
            i++;
          }
        } else {
          currentText += char;
          i++;
        }
      }
      else {
        currentText += char;
        i++;
      }
    } catch (error) {
      // 如果解析出错，将当前字符添加到文本中继续
      console.warn('Markdown parsing error:', error);
      currentText += char;
      i++;
    }
  }
  
  if (currentText) {
    nodes.push({ type: 'text', text: currentText });
  }
  
  return nodes.length > 0 ? nodes : [{ type: 'text', text: '' }];
}

/**
 * 转义HTML特殊字符
 * @param {string} text - 需要转义的文本
 * @returns {string} 转义后的文本
 */
function escapeHtml(text) {
  const div = document.createElement('div');
  div.textContent = text;
  return div.innerHTML;
}

/**
 * 生成富文本组件可用的nodes数组
 * @param {string} markdown - Markdown文本
 * @returns {Array} 富文本节点数组
 */
function generateRichTextNodes(markdown) {
  try {
    return parseMarkdown(markdown);
  } catch (error) {
    console.error('Markdown parsing failed:', error);
    return [{ type: 'text', text: markdown }];
  }
}

module.exports = {
  parseMarkdown,
  parseInlineMarkdown,
  generateRichTextNodes,
  escapeHtml
};