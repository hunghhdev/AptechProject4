<script>
export default {
  name: "MenuItem",
  functional: true,
  props: {
    icon: {
      type: String,
      default: ""
    },
    title: {
      type: String,
      default: ""
    }
  },
  render(h, context) {
    const { icon, title } = context.props;
    const vnodes = [];

    if (icon) {
      vnodes.push(<i class={icon + " svg-icon"}></i>);
    }

    if (title) {
      vnodes.push(<span slot="title" v-t={title + ".title"}></span>);
    }
    return vnodes;
  }
};
</script>
